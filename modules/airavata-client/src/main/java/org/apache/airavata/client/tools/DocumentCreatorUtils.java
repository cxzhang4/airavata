/*
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *
 */

package org.apache.airavata.client.tools;

import java.util.Map;
import java.util.Set;

import org.airavata.appcatalog.cpi.AppCatalogException;
import org.airavata.appcatalog.cpi.GwyResourceProfile;
import org.apache.airavata.model.appcatalog.appdeployment.ApplicationDeploymentDescription;
import org.apache.airavata.model.appcatalog.appdeployment.ApplicationModule;
import org.apache.airavata.model.appcatalog.appdeployment.ApplicationParallelismType;
import org.apache.airavata.model.appcatalog.appinterface.InputDataObjectType;
import org.apache.airavata.model.appcatalog.appinterface.OutputDataObjectType;
import org.apache.airavata.model.appcatalog.computeresource.ComputeResourceDescription;
import org.apache.airavata.model.appcatalog.computeresource.DataMovementInterface;
import org.apache.airavata.model.appcatalog.computeresource.DataMovementProtocol;
import org.apache.airavata.model.appcatalog.computeresource.JobManagerCommand;
import org.apache.airavata.model.appcatalog.computeresource.JobSubmissionInterface;
import org.apache.airavata.model.appcatalog.computeresource.JobSubmissionProtocol;
import org.apache.airavata.model.appcatalog.computeresource.ResourceJobManager;
import org.apache.airavata.model.appcatalog.computeresource.ResourceJobManagerType;
import org.apache.airavata.model.appcatalog.gatewayprofile.ComputeResourcePreference;

public class DocumentCreatorUtils {

	public static void addNewComputeResourcePreference(GwyResourceProfile gatewayProfile,
			String gatewayID, String computeResourceId, String scratchLocation,
			String allocationProjectNumber, boolean overridebyAiravata,
			String preferredBatchQueue, String preferredJobSubmissionProtocol,
			String preferredDataMovementProtocol) throws AppCatalogException {
        org.apache.airavata.model.appcatalog.gatewayprofile.GatewayResourceProfile profile = null;
		boolean gatewayProfileExists=true;
		try {
			profile=gatewayProfile.getGatewayProfile(gatewayID);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (profile==null){
			profile= new org.apache.airavata.model.appcatalog.gatewayprofile.GatewayResourceProfile();
			gatewayProfileExists=false;
		}
		profile.setGatewayID(gatewayID);
		ComputeResourcePreference computeResourcePreference = new ComputeResourcePreference();
		computeResourcePreference.setComputeResourceId(computeResourceId);
		computeResourcePreference.setOverridebyAiravata(overridebyAiravata);
		computeResourcePreference.setAllocationProjectNumber(allocationProjectNumber);
		computeResourcePreference.setPreferredBatchQueue(preferredBatchQueue);
		computeResourcePreference.setPreferredDataMovementProtocol(preferredDataMovementProtocol);
		computeResourcePreference.setPreferredJobSubmissionProtocol(preferredJobSubmissionProtocol);
		computeResourcePreference.setScratchLocation(scratchLocation);
		profile.addToComputeResourcePreferences(computeResourcePreference );
		if (gatewayProfileExists){
			gatewayProfile.updateGatewayProfile(gatewayID, profile);
		}else{
			gatewayProfile.addGatewayProfile(profile);
		}
	}

	public static ApplicationDeploymentDescription createApplicationDeployment(
			String computeResourceId, String appModuleId,
			String executablePath, ApplicationParallelismType parallelism, String appDeploymentDescription) {
		ApplicationDeploymentDescription deployment = new ApplicationDeploymentDescription();
		deployment.setAppDeploymentDescription(appDeploymentDescription);
		deployment.setAppModuleId(appModuleId);
		deployment.setComputeHostId(computeResourceId);
		deployment.setExecutablePath(executablePath);
		deployment.setParallelism(parallelism);
		return deployment;
	}

	public static ApplicationModule createApplicationModule(String appModuleName,
			String appModuleVersion, String appModuleDescription) {
		ApplicationModule module = new ApplicationModule();
		module.setAppModuleDescription(appModuleDescription);
		module.setAppModuleName(appModuleName);
		module.setAppModuleVersion(appModuleVersion);
		return module;
	}

	public static DataMovementInterface createDataMovementInterface(
			String dataMovementInterfaceId,
			DataMovementProtocol dataMovementProtocolType, int priorityOrder) {
		DataMovementInterface dataMovementInterface = new DataMovementInterface();
		dataMovementInterface.setDataMovementInterfaceId(dataMovementInterfaceId);
		dataMovementInterface.setDataMovementProtocol(dataMovementProtocolType);
		dataMovementInterface.setPriorityOrder(priorityOrder);
		return dataMovementInterface;
	}

	public static JobSubmissionInterface createJobSubmissionInterface(
			String jobSubmissionInterfaceId,
			JobSubmissionProtocol jobSubmissionProtocolType, int priorityOrder) {
		JobSubmissionInterface jobSubmissionInterface = new JobSubmissionInterface();
		jobSubmissionInterface.setJobSubmissionInterfaceId(jobSubmissionInterfaceId);
		jobSubmissionInterface.setJobSubmissionProtocol(jobSubmissionProtocolType);
		jobSubmissionInterface.setPriorityOrder(priorityOrder);
		return jobSubmissionInterface;
	}

	public static ComputeResourceDescription createComputeResourceDescription(
			String hostName, Set<String> hostAliases, Set<String> ipAddresses) {
		ComputeResourceDescription host = new ComputeResourceDescription();
		host.setHostName(hostName);
		host.setIpAddresses(ipAddresses);
		host.setHostAliases(hostAliases);
		return host;
	}

	public static ResourceJobManager createResourceJobManager(
			ResourceJobManagerType resourceJobManagerType,
			String jobManagerBinPath,
			Map<JobManagerCommand, String> jobManagerCommands,
			String pushMonitoringEndpoint) {
		ResourceJobManager resourceJobManager=new ResourceJobManager();;
		resourceJobManager.setResourceJobManagerType(resourceJobManagerType);
		resourceJobManager.setJobManagerBinPath(jobManagerBinPath);
		resourceJobManager.setJobManagerCommands(jobManagerCommands);
		resourceJobManager.setPushMonitoringEndpoint(pushMonitoringEndpoint);
		return resourceJobManager;
	}

	public static InputDataObjectType createAppInput (String inputName, String argumentName, String description, String value, org.apache.airavata.model.appcatalog.appinterface.DataType type ){
        InputDataObjectType input = new InputDataObjectType();
        if (inputName!=null) {
			input.setName(inputName);
		}
		if (value!=null) {
			input.setValue(value);
		}
		if (type!=null) {
			input.setType(type);
		}
		if (argumentName!=null) {
			input.setApplicationArgument(argumentName);
		}
		if (description!=null) {
			input.setUserFriendlyDescription(description);
		}
		return input;
    }

    public static OutputDataObjectType createAppOutput (String inputName, String value, org.apache.airavata.model.appcatalog.appinterface.DataType type ){
        OutputDataObjectType outputDataObjectType = new OutputDataObjectType();
        if (inputName!=null) {
			outputDataObjectType.setName(inputName);
		}
		if (value!=null) {
			outputDataObjectType.setValue(value);
		}
		if (type!=null) {
			outputDataObjectType.setType(type);
		}
		return outputDataObjectType;
    }
    
}
