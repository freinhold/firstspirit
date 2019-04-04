package com.reinhold.fs.plugins;

import java.io.IOException;

import de.espirit.common.base.Logging;
import de.espirit.firstspirit.access.BaseContext;
import de.espirit.firstspirit.access.store.ElementDeletedException;
import de.espirit.firstspirit.access.store.LockException;
import de.espirit.firstspirit.access.store.Store;
import de.espirit.firstspirit.access.store.StoreElement;
import de.espirit.firstspirit.access.store.StoreListener;
import de.espirit.firstspirit.access.store.mediastore.Media;
import de.espirit.firstspirit.agency.StoreAgent;
import de.espirit.firstspirit.client.plugin.JavaClientPermanentPlugin;

public class SVGPreviewImagePlugin implements JavaClientPermanentPlugin {

	private static final Class<?> LOGGER = SVGPreviewImagePlugin.class;

	private BaseContext context;
	private SVGPreviewImageStoreListener listener;

	@Override
	public void setUp(BaseContext context) {
		Logging.logInfo("Setting up SVGPreviewImagePlugin", LOGGER);
		this.context = context;
		listener = new SVGPreviewImageStoreListener(context);
		getLiveStore(Store.Type.MEDIASTORE).addStoreListener(listener);
	}

	@Override
	public void tearDown() {
		getLiveStore(Store.Type.MEDIASTORE).removeStoreListener(listener);
	}

	public static Store getLiveStore(BaseContext context,
	        Store.Type storeType) {
		StoreAgent storeAgent = context.requireSpecialist(StoreAgent.TYPE);
		return storeAgent.getStore(storeType);
	}

	private Store getLiveStore(Store.Type storeType) {
		return getLiveStore(context, storeType);
	}

	private class SVGPreviewImageStoreListener implements StoreListener {

		public SVGPreviewImageStoreListener(BaseContext context) {
		}

		@Override
		public void addedToScope(StoreElement element) {
			setPreviewImageForMedia(element);
		}

		@Override
		public void elementChanged(StoreElement element) {
			setPreviewImageForMedia(element);
		}

		@Override
		public void elementMoved(StoreElement arg0, StoreElement arg1) {
		}

		@Override
		public void removedFromScope(StoreElement arg0, StoreElement arg1) {
		}

		@Override
		public void structureChanged(StoreElement element) {
		}

		private void setPreviewImageForMedia(StoreElement element) {
			if (element instanceof Media) {
				Media media = (Media) element;
				if (!media.getRevision().getComment()
				        .startsWith("SVGPreviewImagePlugin")) {
					if (media.getType() == Media.FILE && "svg".equalsIgnoreCase(
					        media.getFile(null).getExtension())) {
						try {
							byte[] imageData = PluginHelper.convertSVGToJPG(
							        media.getFile(null).getInputStream());
							if (!media.isLocked()) {
								media.setLock(true);
							}
							media.getFile(null).setPreviewImage(imageData);
							media.save(
							        "SVGPreviewImagePlugin: Created preview-image for SVG");
							media.setLock(false);
						} catch (IOException | LockException
						        | ElementDeletedException e) {
							Logging.logError("Could not edit element", e,
							        LOGGER);
						}
					}
				}
			}
		}

	}

}
