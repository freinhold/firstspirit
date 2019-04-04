package com.reinhold.fs.plugins;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;

import de.espirit.common.base.Logging;
import de.espirit.firstspirit.access.store.IDProvider;
import de.espirit.firstspirit.access.store.mediastore.File;
import de.espirit.firstspirit.access.store.mediastore.Media;
import de.espirit.firstspirit.client.plugin.toolbar.ToolbarContext;

public class PluginHelper {

	private static final Class<?> LOGGER = PluginHelper.class;

	private PluginHelper() {
		// Hide implicit constructor
	}

	public static InputStream loadCurrentElement(ToolbarContext context) {
		IDProvider element = context.getElement();
		return PluginHelper.loadElement(element);
	}

	public static InputStream loadElement(IDProvider element) {
		if (element instanceof Media) {
			Media media = (Media) element;
			File f = media.getFile(null);
			try {
				return f.getInputStream();
			} catch (IOException e) {
				Logging.logError("Could not read media", e, LOGGER);
			}
		}
		return null;
	}

	public static byte[] convertSVGToJPG(InputStream is) {
		PNGTranscoder t = new PNGTranscoder();
		try {
			is.reset();
		} catch (IOException e) {
			Logging.logError("Could not reset media-content", e, LOGGER);
		}
		TranscoderInput input = new TranscoderInput(is);
		byte[] result = null;

		// Create the transcoder output.
		ByteArrayOutputStream ostream = new ByteArrayOutputStream();
		TranscoderOutput output = new TranscoderOutput(ostream);

		// Save the image.
		try {
			t.transcode(input, output);
			result = ostream.toByteArray();
		} catch (TranscoderException e) {
			Logging.logError("Could not transcode SVG as image", e, LOGGER);
		} finally {
			try {
				ostream.flush();
				ostream.close();
			} catch (IOException e) {
				Logging.logError("Could not transcode SVG as image", e, LOGGER);
			}
		}
		return result;
	}

	public static BufferedImage createImageFromBytes(byte[] imageData) {
		ByteArrayInputStream bais = new ByteArrayInputStream(imageData);
		try {
			return ImageIO.read(bais);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
