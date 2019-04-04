package com.reinhold.fs.plugins;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.reinhold.fs.exceptions.PluginLoadingException;

import de.espirit.firstspirit.access.BaseContext;
import de.espirit.firstspirit.client.plugin.JavaClientEditorialToolbarItemsPlugin;
import de.espirit.firstspirit.client.plugin.toolbar.ExecutableToolbarItem;
import de.espirit.firstspirit.client.plugin.toolbar.JavaClientToolbarItem;

/**
 * Java client plugin example.
 */
public class SVGViewerPlugin implements JavaClientEditorialToolbarItemsPlugin {

	public static final String APP_TITLE = "SVGViewer";
	public static final String APP_NAME = "svg_viewer_plugin";

	/**
	 * List of toolbar items.
	 */
	private final List<ExecutableToolbarItem> items;

	/**
	 * Constructor.
	 */
	public SVGViewerPlugin() {
		this.items = new ArrayList<ExecutableToolbarItem>();
	}

	@Override
	public Collection<? extends JavaClientToolbarItem> getItems() {
		return Collections.unmodifiableCollection(items);
	}

	@Override
	public void setUp(final BaseContext context) {
		try {
			if (PluginAccess.isActive(context, APP_NAME))
				items.add(new SVGViewerPluginToolbarItem());
		} catch (PluginLoadingException e) {
		}
	}

	@Override
	public void tearDown() {
		// Do nothing.
	}

}
