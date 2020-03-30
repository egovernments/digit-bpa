/*
 *    eGov  SmartCity eGovernance suite aims to improve the internal efficiency,transparency,
 *    accountability and the service delivery of the government  organizations.
 *
 *     Copyright (C) 2017  eGovernments Foundation
 *
 *     The updated version of eGov suite of products as by eGovernments Foundation
 *     is available at http://www.egovernments.org
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program. If not, see http://www.gnu.org/licenses/ or
 *     http://www.gnu.org/licenses/gpl.html .
 *
 *     In addition to the terms of the GPL license to be adhered to in using this
 *     program, the following additional terms are to be complied with:
 *
 *         1) All versions of this program, verbatim or modified must carry this
 *            Legal Notice.
 *            Further, all user interfaces, including but not limited to citizen facing interfaces,
 *            Urban Local Bodies interfaces, dashboards, mobile applications, of the program and any
 *            derived works should carry eGovernments Foundation logo on the top right corner.
 *
 *            For the logo, please refer http://egovernments.org/html/logo/egov_logo.png.
 *            For any further queries on attribution, including queries on brand guidelines,
 *            please contact contact@egovernments.org
 *
 *         2) Any misrepresentation of the origin of the material is prohibited. It
 *            is required that all modified versions of this material be marked in
 *            reasonable ways as different from the original version.
 *
 *         3) This license does not grant any rights to any user of the program
 *            with regards to rights under trademark law for use of the trade names
 *            or trademarks of eGovernments Foundation.
 *
 *   In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
 *
 */

package org.egov.infra.filestore.service.impl;

import static java.io.File.separator;
import static org.egov.infra.config.core.ApplicationThreadLocals.getCityCode;
import static org.slf4j.LoggerFactory.getLogger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.egov.infra.config.core.ApplicationThreadLocals;
import org.egov.infra.exception.ApplicationRuntimeException;
import org.egov.infra.filestore.entity.FileStoreMapper;
import org.egov.infra.filestore.service.FileStoreService;
import org.egov.infra.microservice.contract.EgFile;
import org.egov.infra.microservice.contract.StorageResponse;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@Component("egovMicroServiceStore")
public class EgovMicroServiceStore implements FileStoreService {

	private static final String FILESTORE_V1_FILES = "/filestore/v1/files";

	private static final Logger LOG = getLogger(LocalDiskFileStoreService.class);

	private String url;

	private RestTemplate restTemplate;

	@Autowired
	public EgovMicroServiceStore(@Value("${ms.url}") String url) {
		this.restTemplate = new RestTemplate();
		this.url = url + FILESTORE_V1_FILES;
	}

	@Override
	public FileStoreMapper store(File sourceFile, String fileName, String mimeType, String moduleName) {
		return store(sourceFile, fileName, mimeType, moduleName, true);
	}

	@Override
	public FileStoreMapper store(InputStream sourceFileStream, String fileName, String mimeType, String moduleName) {
		return store(sourceFileStream, fileName, mimeType, moduleName, true);
	}

	@Override
	public FileStoreMapper store(File file, String fileName, String mimeType, String moduleName, boolean deleteFile) {
		try {
			HttpHeaders headers = new HttpHeaders();
			LOG.debug(file.getName() + "---------------" + file.length());  
 			headers.setContentType(MediaType.MULTIPART_FORM_DATA);
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("file", new FileSystemResource(file.getName()));
			map.add("tenantId", ApplicationThreadLocals.getTenantID());
			map.add("module", moduleName);
			HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<MultiValueMap<String, Object>>(map,
					headers);
			ResponseEntity<StorageResponse> result = restTemplate.postForEntity(url, request, StorageResponse.class);
			FileStoreMapper fileMapper = new FileStoreMapper(result.getBody().getFiles().get(0).getFileStoreId(),
					fileName);
			LOG.debug("uploaded file  " + fileMapper.getFileStoreId());
			fileMapper.setContentType(mimeType);
			return fileMapper;
		} catch (RestClientException e  ) {
			LOG.error("Error while Saving to FileStore", e);

		}
		return null;
	}

	@Override
	public FileStoreMapper store(InputStream fileStream, String fileName, String mimeType, String moduleName,
			boolean closeStream) {

		try {
			HttpHeaders headers = new HttpHeaders();
			File f = new File(fileName);
			FileUtils.copyToFile(fileStream, f);
			if (closeStream) {
				fileStream.close();
			}
			LOG.info("Uploading ..."+f.getName() + "---------------" + f.length());
			headers.setContentType(MediaType.MULTIPART_FORM_DATA);
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("file", new FileSystemResource(f.getName()));
			map.add("tenantId", ApplicationThreadLocals.getTenantID());
			map.add("module", moduleName);
			HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<MultiValueMap<String, Object>>(map,
					headers);
			ResponseEntity<StorageResponse> result = restTemplate.postForEntity(url, request, StorageResponse.class);
			FileStoreMapper fileMapper = new FileStoreMapper(result.getBody().getFiles().get(0).getFileStoreId(),
					fileName);
			LOG.info("Upload completed for   " + f.getName() +"with filestoreid "+ fileMapper.getFileStoreId());
			fileMapper.setContentType(mimeType);
			if (closeStream)
				f.delete();

			return fileMapper;
		} catch (RestClientException | IOException e) {
			LOG.error("Error while Saving to FileStore", e);

		}
		return null;

	}

	@Override
	public File fetch(FileStoreMapper fileMapper, String moduleName) {
		return this.fetch(fileMapper.getFileStoreId(), moduleName);
	}

	@Override
	public Set<File> fetchAll(Set<FileStoreMapper> fileMappers, String moduleName) {
		return fileMappers.stream().map(fileMapper -> this.fetch(fileMapper.getFileStoreId(), moduleName))
				.collect(Collectors.toSet());
	}

	@Override
	public File fetch(String fileStoreId, String moduleName) {
		OutputStream os =null;
		try {
			String urls = url + "/id?tenantId=" + ApplicationThreadLocals.getTenantID() + "&fileStoreId=" + fileStoreId;
			LOG.info("Downloading.... "+urls);
			ResponseEntity<String> files = restTemplate.getForEntity(urls, String.class);
			byte[] b = files.getBody().getBytes();
			File file = new File(fileStoreId);
			os = new FileOutputStream(file);
			os.write(b);
			os.flush();
			os.close();
			LOG.info("Downloaded .... "+file.getName() +"  "+file.length());
			return file;

		} catch (RestClientException e) {
			throw new RuntimeException("File not found ");
		} catch (IOException e) {
		LOG.error(e.getMessage(),e);
		}
		finally{
			
		}

		return null;

	}

	@Override
	public Path fetchAsPath(String fileStoreId, String moduleName) {
		Path fileDirPath = Paths.get(fetch(fileStoreId, moduleName).getPath());
		return fileDirPath;
	}

	@Override
	public void delete(String fileStoreId, String moduleName) {
		Path fileDirPath = this.getFileDirectoryPath(moduleName);
		if (!fileDirPath.toFile().exists()) {
			Path filePath = this.getFilePath(fileDirPath, fileStoreId);
			try {
				Files.deleteIfExists(filePath);
			} catch (IOException e) {
				throw new ApplicationRuntimeException(
						String.format("Could not remove document %s", filePath.getFileName()), e);
			}
		}
	}

	private Path createNewFilePath(FileStoreMapper fileMapper, String moduleName) throws IOException {
		Path fileDirPath = this.getFileDirectoryPath(moduleName);
		if (!fileDirPath.toFile().exists()) {
			LOG.info("File Store Directory {}/{}/{} not found, creating one", this.url, getCityCode(), moduleName);
			Files.createDirectories(fileDirPath);
			LOG.info("Created File Store Directory {}/{}/{}", this.url, getCityCode(), moduleName);
		}
		return this.getFilePath(fileDirPath, fileMapper.getFileStoreId());
	}

	private Path getFileDirectoryPath(String moduleName) {
		return Paths.get(new StringBuilder().append(this.url).append(separator).append(getCityCode()).append(separator)
				.append(moduleName).toString());
	}

	private Path getFilePath(Path fileDirPath, String fileStoreId) {
		return Paths.get(fileDirPath + separator + fileStoreId);
	}
}