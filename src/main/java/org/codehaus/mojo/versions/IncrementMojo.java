package org.codehaus.mojo.versions;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.shared.release.versions.DefaultVersionInfo;
import org.apache.maven.shared.release.versions.VersionInfo;
import org.apache.maven.shared.release.versions.VersionParseException;
import org.codehaus.mojo.versions.api.GitFuntions;

/**
 * Increments the current project's version, updating the details of any child modules as necessary.
 *
 * @author Pete Cornish
 * @goal increment
 * @aggregator
 * @requiresProject true
 * @requiresDirectInvocation true
 * @since 2.0
 */
public class IncrementMojo
    extends AbstractVersionsSetMojo
{

    @Override
    protected String getNewVersion() throws MojoExecutionException, VersionParseException {
        return incrementVersion( oldVersion );
    }

    /**
     * The Maven Project.
     * @parameter expression="${offset}" default-value=0
     */
    private int offset;
    
    /**
     * Increment the version number.
     * 
     * @param version
     * @return
     * @throws VersionParseException 
     */
    private String incrementVersion( String version ) throws VersionParseException {
    	GitFuntions.setOffset(offset);
    	VersionInfo versionInfo = new DefaultVersionInfo(gitIncrease(version));

    	if (versionInfo.isSnapshot())
    	{
    		return versionInfo.getSnapshotVersionString();
    	}
    	else
    	{
    		return versionInfo.getReleaseVersionString();
    	}
	}
    
    private String gitIncrease(String version){
    	int com=GitFuntions.getVersion();
    	for (int i = version.length()-1; i >= 0; i--) {
			if(version.charAt(i)=='.'){
				return version.substring(0, i+1)+com;
			}
		}
    	return version;
    }

}

