/**
 * This file is part of mycollab-web.
 *
 * mycollab-web is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * mycollab-web is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with mycollab-web.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.esofthead.mycollab.module.project.view.task;

import com.esofthead.mycollab.common.CommentType;
import com.esofthead.mycollab.common.i18n.OptionI18nEnum.StatusI18nEnum;
import com.esofthead.mycollab.configuration.StorageManager;
import com.esofthead.mycollab.core.arguments.ValuedBean;
import com.esofthead.mycollab.core.utils.BeanUtility;
import com.esofthead.mycollab.html.DivLessFormatter;
import com.esofthead.mycollab.module.project.CurrentProjectVariables;
import com.esofthead.mycollab.module.project.ProjectLinkBuilder;
import com.esofthead.mycollab.module.project.ProjectRolePermissionCollections;
import com.esofthead.mycollab.module.project.ProjectTypeConstants;
import com.esofthead.mycollab.module.project.domain.SimpleTask;
import com.esofthead.mycollab.module.project.domain.SimpleTaskList;
import com.esofthead.mycollab.module.project.domain.TaskList;
import com.esofthead.mycollab.module.project.i18n.ProjectCommonI18nEnum;
import com.esofthead.mycollab.module.project.i18n.TaskGroupI18nEnum;
import com.esofthead.mycollab.module.project.service.ProjectTaskService;
import com.esofthead.mycollab.module.project.ui.components.AbstractPreviewItemComp2;
import com.esofthead.mycollab.module.project.ui.components.CommentDisplay;
import com.esofthead.mycollab.module.project.ui.components.DateInfoComp;
import com.esofthead.mycollab.module.project.ui.components.DynaFormLayout;
import com.esofthead.mycollab.module.project.view.settings.component.ProjectUserFormLinkField;
import com.esofthead.mycollab.schedule.email.project.ProjectTaskGroupRelayEmailNotificationAction;
import com.esofthead.mycollab.spring.ApplicationContextUtil;
import com.esofthead.mycollab.utils.TooltipHelper;
import com.esofthead.mycollab.vaadin.AppContext;
import com.esofthead.mycollab.vaadin.events.HasPreviewFormHandlers;
import com.esofthead.mycollab.vaadin.mvp.ViewComponent;
import com.esofthead.mycollab.vaadin.mvp.ViewScope;
import com.esofthead.mycollab.vaadin.ui.*;
import com.esofthead.mycollab.vaadin.ui.form.field.DefaultViewField;
import com.esofthead.mycollab.vaadin.ui.form.field.LinkViewField;
import com.hp.gagawa.java.elements.A;
import com.hp.gagawa.java.elements.Div;
import com.hp.gagawa.java.elements.Img;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.UUID;

/**
 * @author MyCollab Ltd.
 * @since 1.0
 */
@ViewComponent(scope = ViewScope.PROTOTYPE)
public class TaskGroupReadViewImpl extends
        AbstractPreviewItemComp2<SimpleTaskList> implements TaskGroupReadView {
    private static final long serialVersionUID = 1L;

    private static final Logger LOG = LoggerFactory
            .getLogger(TaskGroupReadViewImpl.class);

    private CommentDisplay commentList;

    private TaskGroupHistoryLogList historyList;

    private DateInfoComp dateInfoComp;

    private PeopleInfoComp peopleInfoComp;

    public TaskGroupReadViewImpl() {
        super(AppContext
                        .getMessage(TaskGroupI18nEnum.FORM_VIEW_TASKGROUP_TITLE),
                MyCollabResource.newResource(WebResourceIds._22_project_task_group));
    }

    @Override
    public HasPreviewFormHandlers<SimpleTaskList> getPreviewFormHandlers() {
        return this.previewForm;
    }

    @Override
    protected void initRelatedComponents() {
        commentList = new CommentDisplay(CommentType.PRJ_TASK_LIST,
                CurrentProjectVariables.getProjectId(), true, true,
                ProjectTaskGroupRelayEmailNotificationAction.class);
        commentList.setWidth("100%");
        commentList.setMargin(true);

        historyList = new TaskGroupHistoryLogList();
        historyList.setMargin(true);

        dateInfoComp = new DateInfoComp();
        addToSideBar(dateInfoComp);

        peopleInfoComp = new PeopleInfoComp();
        addToSideBar(peopleInfoComp);
    }

    @Override
    protected void onPreviewItem() {
        commentList.loadComments("" + beanItem.getId());
        historyList.loadHistory(beanItem.getId());

        peopleInfoComp.displayEntryPeople(beanItem);
        dateInfoComp.displayEntryDateTime(beanItem);
    }

    @Override
    protected String initFormTitle() {
        if (StatusI18nEnum.Closed.name().equals(beanItem.getStatus())) {
            this.addLayoutStyleName(UIConstants.LINK_COMPLETED);
        }
        return beanItem.getName();
    }

    @Override
    protected AdvancedPreviewBeanForm<SimpleTaskList> initPreviewForm() {
        return new AdvancedPreviewBeanForm<>();
    }

    @Override
    protected IFormLayoutFactory initFormLayoutFactory() {
        return new DynaFormLayout(ProjectTypeConstants.TASK_LIST,
                TaskGroupDefaultFormLayoutFactory.getForm(),
                TaskList.Field.name.name());
    }

    @Override
    protected ComponentContainer createButtonControls() {
        return (new ProjectPreviewFormControlsGenerator<>(
                previewForm))
                .createButtonControls(
                        ProjectPreviewFormControlsGenerator.ADD_BTN_PRESENTED
                                | ProjectPreviewFormControlsGenerator.ASSIGN_BTN_PRESENTED
                                | ProjectPreviewFormControlsGenerator.CLONE_BTN_PRESENTED
                                | ProjectPreviewFormControlsGenerator.DELETE_BTN_PRESENTED
                                | ProjectPreviewFormControlsGenerator.EDIT_BTN_PRESENTED,
                        ProjectRolePermissionCollections.TASKS);
    }

    @Override
    protected ComponentContainer createBottomPanel() {
        final TabsheetLazyLoadComp tabContainer = new TabsheetLazyLoadComp();

        tabContainer.addTab(commentList, AppContext
                        .getMessage(ProjectCommonI18nEnum.TAB_COMMENT),
                MyCollabResource
                        .newResource(WebResourceIds._16_project_gray_comment));

        tabContainer.addTab(historyList, AppContext
                        .getMessage(ProjectCommonI18nEnum.TAB_HISTORY),
                MyCollabResource
                        .newResource(WebResourceIds._16_project_gray_history));

        return tabContainer;
    }

    @Override
    public SimpleTaskList getItem() {
        return beanItem;
    }

    @Override
    protected AbstractBeanFieldGroupViewFieldFactory<SimpleTaskList> initBeanFormFieldFactory() {
        return new AbstractBeanFieldGroupViewFieldFactory<SimpleTaskList>(
                previewForm) {
            private static final long serialVersionUID = 1L;

            @Override
            protected Field<?> onCreateField(final Object propertyId) {
                if (TaskList.Field.milestoneid.equalTo(propertyId)) {
                    return new LinkViewField(
                            beanItem.getMilestoneName(),
                            ProjectLinkBuilder
                                    .generateMilestonePreviewFullLink(
                                            beanItem.getProjectid(),
                                            beanItem.getMilestoneid()),
                            MyCollabResource
                                    .newResourceLink(WebResourceIds._16_project_milestone));
                } else if (TaskList.Field.owner.equalTo(propertyId)) {
                    return new ProjectUserFormLinkField(beanItem.getOwner(),
                            beanItem.getOwnerAvatarId(),
                            beanItem.getOwnerFullName());
                } else if (TaskList.Field.description.equalTo(propertyId)) {
                    return new DefaultViewField(beanItem.getDescription(),
                            ContentMode.HTML);
                } else if (TaskList.Field.groupindex.equalTo(propertyId)) {
                    return new SubTasksViewField();
                }

                return null;
            }
        };
    }

    class SubTasksViewField extends CustomField {

        @Override
        protected Component initContent() {
            ProjectTaskService taskService = ApplicationContextUtil.getSpringBean(ProjectTaskService.class);
            List<SimpleTask> subTasks = taskService.findSubTasksOfGroup(beanItem.getId(), AppContext.getAccountId());
            if (CollectionUtils.isNotEmpty(subTasks)) {
                Div div = new Div();
                for (SimpleTask task : subTasks) {
                    Div taskDiv = buildTaskDiv(task);
                    div.appendChild(taskDiv);
                }
                return new Label(div.write(), ContentMode.HTML);
            } else {
                return new Label();
            }
        }

        private Div buildTaskDiv(SimpleTask task) {
            String linkName = String.format("[%s-%d] %s", CurrentProjectVariables.getShortName(), task.getTaskkey(), task
                    .getTaskname());
            A taskLink = new A().setHref(ProjectLinkBuilder.generateTaskPreviewFullLink(task.getTaskkey(),
                    CurrentProjectVariables.getShortName())).appendText(linkName);
            if (task.isCompleted()) {
                taskLink.setCSSClass("completed");
            } else if (task.isOverdue()) {
                taskLink.setCSSClass("overdue");
            } else if (task.isPending()) {
                taskLink.setCSSClass("pending");
            }

            String uid = UUID.randomUUID().toString();
            taskLink.setId("tag" + uid);
            String arg17 = "'" + uid + "'";
            String arg18 = "'" + ProjectTypeConstants.TASK + "'";
            String arg19 = "'" + task.getId() + "'";
            String arg20 = "'" + AppContext.getSiteUrl() + "tooltip/'";
            String arg21 = "'" + AppContext.getAccountId() + "'";
            String arg22 = "'" + AppContext.getSiteUrl() + "'";
            String arg23 = AppContext.getSession().getTimezone();
            String arg24 = "'" + AppContext.getUserLocale().toString() + "'";

            String mouseOverFunc = String.format(
                    "return overIt(%s,%s,%s,%s,%s,%s,%s,%s);", arg17, arg18, arg19,
                    arg20, arg21, arg22, arg23, arg24);
            taskLink.setAttribute("onmouseover", mouseOverFunc);

            String avatarLink = StorageManager.getAvatarLink(task.getAssignUserAvatarId(), 16);
            Img avatarImg = new Img(task.getAssignUserFullName(), avatarLink).setTitle(task.getAssignUserFullName());
            if (StringUtils.isNotBlank(task.getAssignuser())) {
                A avatarDiv = new A().setHref(ProjectLinkBuilder.generateProjectMemberFullLink(CurrentProjectVariables
                        .getProjectId(), task.getAssignuser()))
                        .appendChild(avatarImg);
                return new Div().appendChild(avatarDiv, DivLessFormatter.EMPTY_SPACE(), taskLink, DivLessFormatter.EMPTY_SPACE(),
                        TooltipHelper.buildDivTooltipEnable(uid)).setStyle("display: list-item; " +
                        "list-style-position: " +
                        "inside;");
            } else {
                return new Div().appendChild(avatarImg, DivLessFormatter.EMPTY_SPACE(), taskLink, DivLessFormatter.EMPTY_SPACE(),
                        TooltipHelper.buildDivTooltipEnable(uid)).setStyle("display: list-item; " +
                        "list-style-position: " +
                        "inside;");
            }
        }

        @Override
        public Class getType() {
            return String.class;
        }
    }

    private class PeopleInfoComp extends VerticalLayout {
        private static final long serialVersionUID = 1L;

        public void displayEntryPeople(ValuedBean bean) {
            this.removeAllComponents();
            this.setSpacing(true);
            this.setMargin(new MarginInfo(false, false, false, true));

            Label peopleInfoHeader = new Label(
                    AppContext
                            .getMessage(ProjectCommonI18nEnum.SUB_INFO_PEOPLE));
            peopleInfoHeader.setStyleName("info-hdr");
            this.addComponent(peopleInfoHeader);

            GridLayout layout = new GridLayout(2, 2);
            layout.setSpacing(true);
            layout.setWidth("100%");
            layout.setMargin(new MarginInfo(false, false, false, true));
            try {
                Label createdLbl = new Label(
                        AppContext
                                .getMessage(ProjectCommonI18nEnum.ITEM_CREATED_PEOPLE));
                createdLbl.setSizeUndefined();
                layout.addComponent(createdLbl, 0, 0);

                String createdUserName = (String) PropertyUtils.getProperty(
                        bean, "createduser");
                String createdUserAvatarId = (String) PropertyUtils
                        .getProperty(bean, "createdUserAvatarId");
                String createdUserDisplayName = (String) PropertyUtils
                        .getProperty(bean, "createdUserFullName");

                UserLink createdUserLink = new UserLink(createdUserName,
                        createdUserAvatarId, createdUserDisplayName);
                layout.addComponent(createdUserLink, 1, 0);
                layout.setColumnExpandRatio(1, 1.0f);

                Label assigneeLbl = new Label(
                        AppContext
                                .getMessage(ProjectCommonI18nEnum.ITEM_ASSIGN_PEOPLE));
                assigneeLbl.setSizeUndefined();
                layout.addComponent(assigneeLbl, 0, 1);
                String assignUserName = (String) PropertyUtils.getProperty(
                        bean, "owner");
                String assignUserAvatarId = (String) PropertyUtils.getProperty(
                        bean, "ownerAvatarId");
                String assignUserDisplayName = (String) PropertyUtils
                        .getProperty(bean, "ownerFullName");

                UserLink assignUserLink = new UserLink(assignUserName,
                        assignUserAvatarId, assignUserDisplayName);
                layout.addComponent(assignUserLink, 1, 1);
            } catch (Exception e) {
                LOG.error("Can not build user link {} ",
                        BeanUtility.printBeanObj(bean));
            }

            this.addComponent(layout);

        }
    }
}
