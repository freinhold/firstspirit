package com.reinhold.fs.plugins;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import de.espirit.firstspirit.client.plugin.toolbar.ExecutableToolbarItem;
import de.espirit.firstspirit.client.plugin.toolbar.ToolbarContext;

/**
 * The type Create experiment site architect toolbar item.
 */
public final class SVGViewerPluginToolbarItem implements ExecutableToolbarItem {

	/**
	 * Instantiates a new ConventionViewToolbarItem.
	 */
	public SVGViewerPluginToolbarItem() {
	}

	@Override
	public void execute(final ToolbarContext context) {
		new SVGWindow().show(context);
	}

	@Override
	public String getLabel(final ToolbarContext context) {
		return SVGViewerPlugin.APP_TITLE;
	}

	@Override
	public boolean isEnabled(final ToolbarContext context) {
		return true;
	}

	@Override
	public boolean isVisible(final ToolbarContext context) {
		return true;
	}

	@Override
	public Icon getIcon(final ToolbarContext context) {
		return new ImageIcon(this.getClass().getResource("/icon.png"));
	}

	@Override
	public Icon getPressedIcon(final ToolbarContext context) {
		return new ImageIcon(this.getClass().getResource("/icon-pressed.png"));
	}

	@Override
	public Icon getRollOverIcon(final ToolbarContext context) {
		return new ImageIcon(this.getClass().getResource("/icon.png"));
	}
}
