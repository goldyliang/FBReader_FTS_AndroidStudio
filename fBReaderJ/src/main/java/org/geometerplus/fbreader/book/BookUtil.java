/*
 * Copyright (C) 2007-2014 Geometer Plus <contact@geometerplus.com>
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA
 * 02110-1301, USA.
 */

package org.geometerplus.fbreader.book;

import android.util.Log;

import java.io.InputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

import org.geometerplus.fbreader.bookmodel.BookModel;
import org.geometerplus.fbreader.formats.BuiltinFormatPlugin;
import org.geometerplus.zlibrary.core.filesystem.*;
import org.geometerplus.zlibrary.core.image.ZLImage;

import org.geometerplus.fbreader.bookmodel.BookReadingException;
import org.geometerplus.fbreader.formats.FormatPlugin;
import org.geometerplus.fbreader.formats.PluginCollection;
import org.geometerplus.zlibrary.text.model.ZLTextModel;
import org.geometerplus.zlibrary.text.model.ZLTextParagraph;

public abstract class BookUtil {
	private static final WeakReference<ZLImage> NULL_IMAGE = new WeakReference<ZLImage>(null);
	private static final WeakHashMap<ZLFile,WeakReference<ZLImage>> ourCovers =
		new WeakHashMap<ZLFile,WeakReference<ZLImage>>();

	public static ZLImage getCover(Book book) {
		if (book == null) {
			return null;
		}
		synchronized (book) {
			return getCover(book.File);
		}
	}

	public static ZLImage getCover(ZLFile file) {
		WeakReference<ZLImage> cover = ourCovers.get(file);
		if (cover == NULL_IMAGE) {
			return null;
		} else if (cover != null) {
			final ZLImage image = cover.get();
			if (image != null) {
				return image;
			}
		}
		ZLImage image = null;
		try {
			image = PluginCollection.Instance().getPlugin(file).readCover(file);
		} catch (Exception e) {
			// ignore
		}
		ourCovers.put(file, image != null ? new WeakReference<ZLImage>(image) : NULL_IMAGE);
		return image;
	}

	public static String getAnnotation(Book book) {
		try {
			return book.getPlugin().readAnnotation(book.File);
		} catch (BookReadingException e) {
			return null;
		}
	}

	public static ZLResourceFile getHelpFile() {
		final Locale locale = Locale.getDefault();

		ZLResourceFile file = ZLResourceFile.createResourceFile(
			"data/intro/intro-" + locale.getLanguage() + "_" + locale.getCountry() + ".epub"
		);
		if (file.exists()) {
			return file;
		}

		file = ZLResourceFile.createResourceFile(
			"data/intro/intro-" + locale.getLanguage() + ".epub"
		);
		if (file.exists()) {
			return file;
		}

		return ZLResourceFile.createResourceFile("data/intro/intro-en.epub");
	}

	public static UID createUid(ZLFile file, String algorithm) {
		InputStream stream = null;

		try {
			final MessageDigest hash = MessageDigest.getInstance(algorithm);
			stream = file.getInputStream();

			final byte[] buffer = new byte[2048];
			while (true) {
				final int nread = stream.read(buffer);
				if (nread == -1) {
					break;
				}
				hash.update(buffer, 0, nread);
			}

			final Formatter f = new Formatter();
			for (byte b : hash.digest()) {
				f.format("%02X", b & 0xFF);
			}
			return new UID(algorithm, f.toString());
		} catch (IOException e) {
			return null;
		} catch (NoSuchAlgorithmException e) {
			return null;
		} finally {
			if (stream != null) {
				try {
					stream.close();
				} catch (IOException e) {
				}
			}
		}
	}

	public static ZLTextModel getModelText (Book book) {
		BookModel model = null;

		try {
			FormatPlugin plugin = book.getPlugin();

			if (plugin instanceof BuiltinFormatPlugin) {
				model = BookModel.createModel(book);
				Log.v("model class", model.getClass().toString());
			}

			if (model == null) return null;

			return model.getTextModel();
		} catch (BookReadingException e) {
			e.printStackTrace();
			return null;
		}

	}

	public static int getBookParagraphNum (Book book) {
		ZLTextModel model = getModelText(book);

		if (model == null)
			return 0;
		else
			return model.getParagraphsNumber();
	}

	public static String getParagraph (ZLTextModel modelText, int i) {

		ZLTextParagraph p = modelText.getParagraph(i);

		ZLTextParagraph.EntryIterator iter = p.iterator();

		while (iter.next())
			if (iter.getType() == ZLTextParagraph.Entry.TEXT) {

				char c[] = iter.getTextData();

				if (c != null) {
					int start = iter.getTextOffset();
					int len = iter.getTextLength();
					String txt = new String(c, start, len);

					return txt;
				}
			}

		return null;
	}
}
