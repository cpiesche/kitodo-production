/*
 * (c) Kitodo. Key to digital objects e. V. <contact@kitodo.org>
 *
 * This file is part of the Kitodo project.
 *
 * It is licensed under GNU General Public License version 3 or later.
 *
 * For the full copyright and license information, please read the
 * GPL3-License.txt file that was distributed with this source code.
 */

package org.kitodo.dto;

import java.io.InputStream;
import java.util.Objects;

import org.kitodo.services.ServiceManager;

public class TemplateDTO extends BaseTemplateDTO {

    private boolean active;
    private WorkflowDTO workflow;
    private boolean canBeUsedForProcess;

    /**
     * Get diagram image.
     *
     * @return value of diagramImage
     */
    public InputStream getDiagramImage() {
        if (Objects.nonNull(this.workflow)) {
            return new ServiceManager().getTemplateService().getTasksDiagram(this.workflow.getFileName());
        }
        return new ServiceManager().getTemplateService().getTasksDiagram("");
    }

    /**
     * Get active.
     *
     * @return value of active
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Set active.
     *
     * @param active
     *            as boolean
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
<<<<<<< HEAD
     * Set workflow.
     *
     * @param workflow as org.kitodo.dto.WorkflowDTO
     */
    public void setWorkflow(WorkflowDTO workflow) {
        this.workflow = workflow;
    }

    /**
     * Get workflow.
     *
     * @return value of workflow
     */
    public WorkflowDTO getWorkflow() {
        return workflow;
    }

    /**
     * Get information if template doesn't contain unreachable tasks and is active.
     *
     * @return true or false
     */
    public boolean isCanBeUsedForProcess() {
        return canBeUsedForProcess;
    }

    /**
     * Set information if template doesn't contain unreachable tasks and is active.
     *
     * @param canBeUsedForProcess
     *            as boolean
     */
    public void setCanBeUsedForProcess(boolean canBeUsedForProcess) {
        this.canBeUsedForProcess = canBeUsedForProcess;
    }
}
