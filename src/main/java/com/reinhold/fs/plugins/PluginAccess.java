package com.reinhold.fs.plugins;

import java.util.Set;

import com.reinhold.fs.exceptions.PluginLoadingException;

import de.espirit.firstspirit.access.BaseContext;
import de.espirit.firstspirit.access.editor.value.SimpleOption;
import de.espirit.firstspirit.access.store.Store;
import de.espirit.firstspirit.access.store.globalstore.GlobalStoreRoot;
import de.espirit.firstspirit.access.store.globalstore.ProjectProperties;
import de.espirit.firstspirit.agency.StoreAgent;
import de.espirit.firstspirit.forms.FormData;
import de.espirit.firstspirit.forms.FormField;

public class PluginAccess {

	public static final String ACTIVE_PLUGIN_FIELD_NAME = "ps_active_plugins";

	private PluginAccess() {
		// Hide implicit constructor
	}

	@SuppressWarnings("unchecked")
	public static boolean isActive(BaseContext context, String pluginName)
	        throws PluginLoadingException {

		if (pluginName == null || "".equals(pluginName.trim())) {
			throw new PluginLoadingException(
			        "Error while loading plugin - pluginName is undefined!");
		}

		StoreAgent agent = context.requireSpecialist(StoreAgent.TYPE);
		GlobalStoreRoot gsr = (GlobalStoreRoot) agent
		        .getStore(Store.Type.GLOBALSTORE);
		ProjectProperties ps = gsr.getProjectProperties();
		boolean active = false;

		FormData frmData = ps.getFormData();
		try {
			if (frmData.getForm().findEditor(ACTIVE_PLUGIN_FIELD_NAME) == null)
				return false;

			// check active plugins
			Object field = frmData.get(null, ACTIVE_PLUGIN_FIELD_NAME);
			FormField<Set<SimpleOption>> ff = (FormField<Set<SimpleOption>>) field;
			if (!ff.isEmpty()) {
				Set<SimpleOption> options = ff.get();
				for (SimpleOption option : options) {
					if (option.getValue() != null
					        && option.getValue().equals(pluginName)) {
						active = true;
						break;
					}
				}
			}
		} catch (Exception e) {
			throw new PluginLoadingException(
			        "Error while loading plugin " + pluginName + "!", e);
		}
		return active;
	}
}
