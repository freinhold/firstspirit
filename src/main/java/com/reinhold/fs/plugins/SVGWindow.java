package com.reinhold.fs.plugins;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.InputStream;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import de.espirit.common.base.Logging;
import de.espirit.firstspirit.agency.ProjectAgent;
import de.espirit.firstspirit.client.plugin.toolbar.ToolbarContext;

/**
 * The type Console window.
 */
public final class SVGWindow {

	private static final Class<?> LOGGER = SVGWindow.class;

	private final String CANCEL_COMMAND = "Close";

	private final Dimension screenSize;

	private ToolbarContext context;

	private JFrame window;
	private JButton cancelButton;

	public SVGWindow() {

		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	}

	/**
	 * Gets frame.
	 *
	 * @param projectName
	 *            the FirstSpirit project name
	 * @return the frame
	 */

	public JFrame getFrame(ToolbarContext context) {
		if (window == null) {
			this.context = context;
			final ProjectAgent projectAgent = context
			        .requireSpecialist(ProjectAgent.TYPE);
			final String projectName = projectAgent.getName();
			window = setupFrame(projectName);

			final Container contentPane = window.getContentPane();

			cancelButton = new JButton(CANCEL_COMMAND);
			cancelButton.setActionCommand(CANCEL_COMMAND);
			cancelButton.addActionListener(new ClickListener(window));

			JPanel buttonPane = new JPanel();

			buttonPane
			        .setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
			buttonPane.add(Box.createHorizontalGlue());
			buttonPane.add(cancelButton);

			BoxLayout layout = new BoxLayout(buttonPane, BoxLayout.LINE_AXIS);
			buttonPane.setLayout(layout);

			JPanel mainPane = new JPanel();

			Logging.logInfo("Drawing SVG", LOGGER);

			InputStream is = PluginHelper.loadCurrentElement(context);

			BufferedImage picture = PluginHelper
			        .createImageFromBytes(PluginHelper.convertSVGToJPG(is));
			JLabel picLabel = new JLabel(new ImageIcon(picture));
			mainPane.add(picLabel);

			contentPane.add(mainPane, BorderLayout.CENTER);
			contentPane.add(buttonPane, BorderLayout.SOUTH);

			window.pack();
		}
		return window;
	}

	private JFrame setupFrame(final String projectName) {
		final String appName = createAppName(projectName);
		final JFrame frame = new JFrame(appName);
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

		// Dynamic size
		final Dimension preferredSize = new Dimension(screenSize.width >> 1,
		        screenSize.height >> 1);
		frame.setPreferredSize(preferredSize);

		return frame;
	}

	private String createAppName(final String projectName) {
		final StringBuilder appName = new StringBuilder(
		        SVGViewerPlugin.APP_TITLE);
		if (projectName != null) {
			appName.append(" / ");
			appName.append(projectName);
		}
		return appName.toString();
	}

	/**
	 * Show window.
	 *
	 * @param projectName
	 *            the project name
	 */
	public void show(ToolbarContext context) {
		if (getFrame(context) != null) {
			window.setVisible(true);
			window.requestFocus();
		} else {
			JOptionPane.showMessageDialog(null, "Error while showing SVG-Image",
			        "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private class ClickListener implements ActionListener {

		private JFrame parentFrame;

		public ClickListener(JFrame frame) {
			parentFrame = frame;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (CANCEL_COMMAND.equals(e.getActionCommand())) {
				parentFrame.dispose();
			}
			parentFrame.repaint();
		}

	}

}
