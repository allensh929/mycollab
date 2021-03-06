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
package com.esofthead.mycollab.module.crm.view.cases;

import com.esofthead.mycollab.common.i18n.GenericI18Enum;
import com.esofthead.mycollab.module.crm.i18n.CaseI18nEnum;
import com.esofthead.mycollab.module.crm.ui.components.HistoryLogWindow;
import com.esofthead.mycollab.module.user.ui.components.UserHistoryFieldFormat;
import com.esofthead.mycollab.utils.FieldGroupFomatter;

/**
 * 
 * @author MyCollab Ltd.
 * @since 1.0
 * 
 */
public class CaseHistoryLogWindow extends HistoryLogWindow {
	private static final long serialVersionUID = 1L;

	public static final FieldGroupFomatter caseFormatter;

	static {
		caseFormatter = new FieldGroupFomatter();

		caseFormatter.generateFieldDisplayHandler("priority",
				CaseI18nEnum.FORM_PRIORITY);
		caseFormatter.generateFieldDisplayHandler("status",
				CaseI18nEnum.FORM_STATUS);
		caseFormatter.generateFieldDisplayHandler("accountid",
				CaseI18nEnum.FORM_ACCOUNT);
		caseFormatter.generateFieldDisplayHandler("phonenumber",
				CaseI18nEnum.FORM_PHONE);
		caseFormatter.generateFieldDisplayHandler("origin",
				CaseI18nEnum.FORM_ORIGIN);
		caseFormatter.generateFieldDisplayHandler("type",
				CaseI18nEnum.FORM_TYPE);
		caseFormatter.generateFieldDisplayHandler("reason",
				CaseI18nEnum.FORM_REASON);
		caseFormatter.generateFieldDisplayHandler("subject",
				CaseI18nEnum.FORM_SUBJECT);
		caseFormatter.generateFieldDisplayHandler("email",
				CaseI18nEnum.FORM_EMAIL);
		caseFormatter.generateFieldDisplayHandler("assignuser",
				GenericI18Enum.FORM_ASSIGNEE, new UserHistoryFieldFormat());
		caseFormatter.generateFieldDisplayHandler("description",
				GenericI18Enum.FORM_DESCRIPTION);
		caseFormatter.generateFieldDisplayHandler("resolution",
				CaseI18nEnum.FORM_RESOLUTION);
	}

	public CaseHistoryLogWindow(String module, String type) {
		super(module, type);
	}

	@Override
	protected FieldGroupFomatter buildFormatter() {
		return caseFormatter;
	}

}
