/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.rakam.ui.customreport;

import org.rakam.config.EncryptionConfig;
import org.rakam.server.http.HttpService;
import org.rakam.server.http.annotations.ApiOperation;
import org.rakam.server.http.annotations.ApiParam;
import org.rakam.server.http.annotations.Authorization;
import org.rakam.server.http.annotations.BodyParam;
import org.rakam.server.http.annotations.IgnoreApi;
import org.rakam.server.http.annotations.JsonRequest;
import org.rakam.ui.ProtectEndpoint;
import org.rakam.ui.UIPermissionParameterProvider;
import org.rakam.ui.UIPermissionParameterProvider.Project;
import org.rakam.util.SuccessMessage;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import java.util.List;


@Path("/ui/custom-report")
@IgnoreApi
public class CustomReportHttpService extends HttpService {

    private final CustomReportMetadata metadata;

    @Inject
    public CustomReportHttpService(CustomReportMetadata metadata) {
        this.metadata = metadata;
    }

    @JsonRequest
    @Path("/list")
    @ApiOperation(value = "List reports", tags = "rakam-ui", authorizations = @Authorization(value = "read_key"))
    public List<CustomReport> list(@ApiParam("report_type") String reportType,
            @Named("user_id") Project project) {
        return metadata.list(reportType, project.project);
    }

    @GET
    @Path("/types")
    @JsonRequest
    @ApiOperation(value = "List report types", tags = "rakam-ui", authorizations = @Authorization(value = "read_key"))
    public List<String> types(@Named("user_id") Project project) {
        return metadata.types(project.project);
    }


    @ApiOperation(value = "Create reports", tags = "rakam-ui", authorizations = @Authorization(value = "read_key"),
            response = SuccessMessage.class, request = CustomReport.class)
    @JsonRequest
    @ProtectEndpoint(writeOperation = true)
    @Path("/create")
    public SuccessMessage create(@Named("user_id") Project project, @BodyParam CustomReport report) {
        metadata.save(project.userId, project.project, report);
        return SuccessMessage.success();
    }

    @JsonRequest
    @Path("/update")
    @ProtectEndpoint(writeOperation = true)
    @ApiOperation(value = "Update reports", tags = "rakam-ui", authorizations = @Authorization(value = "read_key"))
    public SuccessMessage update(@Named("user_id") Project project, @BodyParam CustomReport report) {
        metadata.update(project.project, report);
        return SuccessMessage.success();
    }

    @JsonRequest
    @Path("/delete")
    @ApiOperation(value = "Delete reports", tags = "rakam-ui", authorizations = @Authorization(value = "read_key"))
    @ProtectEndpoint(writeOperation = true)
    public SuccessMessage delete(@Named("user_id") Project project,
                               @ApiParam("report_type") String reportType,
                               @ApiParam("name") String name) {
        metadata.delete(reportType, project.project, name);

        return SuccessMessage.success();
    }

    @JsonRequest
    @Path("/get")
    @ApiOperation(value = "Get reports", tags = "rakam-ui", authorizations = @Authorization(value = "read_key"))
    public Object get(@ApiParam("report_type") String reportType,
            @Named("user_id") Project project,
                      @ApiParam(value = "name") String name) {
        return metadata.get(reportType, project.project, name);
    }
}
