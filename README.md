## The following steps should successfully build FBReader on Android Studio

#####Tested on a fresh installation of Windows 8.1 x64 using Android Studio v1.02 build 1653844, jdk-7u71-windows-x64, and android-ndk-r10d.

1. Clone the project into your %userprofile%\AndroidStudioProjects\

1. Import the project into Android Studio

1. Perform a project level gradle sync (CTR+SHIFT+A and search for "project sync", double click the first result).  
Android Studio may complain about not finding a git.exe in your path, but this will not prevent you from building the project

1. Install Android SDK Platform API 14 when prompted

1. Install Android SDK Platform API 5 when prompted

1. Install Android SDK Platform API 11 when prompted

1. FBReader has now been successfully built using Android Studio, but is missing the necessary binaries (*.so)

## Compiling the binaries

1. To compile the binaries from the source, navigate to where you extracted the NDK

1. Execute the following command in your shell:

    ndk-build -C %userprofile%\AndroidStudioProjects\FBReaderJ\fBReaderJ\src\main\jni  

   The binaries should be automatically placed into the appropriate directories (%userprofile%\AndroidStudioProjects\FBReaderJ\fBReaderJ\src\main\lib)

1. Finally, rebuild the project. FBReader should now run without issue on the emulator/on your device.

## Generating the introductory help epubs

This step is optional. If you would like to see the introductory help epub when FBReader starts, execute the following commands in your shell and rebuild the project:
   
    cd %userprofile%\AndroidStudioProjects\FBReaderJ\help\
    generate.py proto html ..\fBReaderJ\src\main\assets\data\intro\

<br>

ECLIPSE ANDROID PROJECT IMPORT SUMMARY
======================================

Manifest Merging:
-----------------
Your project uses libraries that provide manifests, and your Eclipse
project did not explicitly turn on manifest merging. In Android Gradle
projects, manifests are always merged (meaning that contents from your
libraries' manifests will be merged into the app manifest. If you had
manually copied contents from library manifests into your app manifest
you may need to remove these for the app to build correctly.

Ignored Files:
--------------
The following files were *not* copied into the new Gradle project; you
should evaluate whether these are still needed in your project and if
so manually move them:

From AmbilWarna:

* AmbilWarna.iml
* build.xml

From FBReaderJ:

* .gitignore
* .idea\
* .idea\.name
* .idea\FBReaderJ.iml
* .idea\compiler.xml
* .idea\copyright\
* .idea\copyright\profiles_settings.xml
* .idea\encodings.xml
* .idea\misc.xml
* .idea\modules.xml
* .idea\scopes\
* .idea\scopes\scope_settings.xml
* .idea\vcs.xml
* .idea\workspace.xml
* AndroidManifest.xml.pattern
* ChangeLog
* HowToBuild
* TODO.1.5
* TODO.2pages
* TODO.bookInfo
* TODO.catalogs
* TODO.dictionary
* TODO.favorites
* TODO.highlighting
* TODO.honeycomb
* TODO.library
* TODO.libraryService
* TODO.litres
* TODO.network
* TODO.selection
* TODO.tips
* VERSION
* ant.properties
* build.xml
* docs\
* docs\cygwin_installation_and_configuration.pdf
* help\
* help\generate.py
* help\html\
* help\html\be.html
* help\html\cs.html
* help\html\en.html
* help\html\es.html
* help\html\fr.html
* help\html\hy.html
* help\html\nl.html
* help\html\pt.html
* help\html\ru.html
* help\html\uk.html
* help\proto\
* help\proto\container.xml
* help\proto\content.opf
* help\proto\fbreader.png
* help\proto\style.css
* icons-misc\
* icons-misc\AUDIO\
* icons-misc\AUDIO\audio.cdr
* icons-misc\AUDIO\audio_1024x1024.png
* icons-misc\AUDIO\audio_114x114.png
* icons-misc\AUDIO\audio_128x128.png
* icons-misc\AUDIO\audio_144x144.png
* icons-misc\AUDIO\audio_256x256.png
* icons-misc\AUDIO\audio_32x32.png
* icons-misc\AUDIO\audio_48x48.png
* icons-misc\AUDIO\audio_512x512.png
* icons-misc\AUDIO\audio_64x64.png
* icons-misc\AUDIO\audio_72x72.png
* icons-misc\BOOKMARKS\
* icons-misc\BOOKMARKS\bookmarks.cdr
* icons-misc\BOOKMARKS\bookmarks_1024x1024.png
* icons-misc\BOOKMARKS\bookmarks_114x114.png
* icons-misc\BOOKMARKS\bookmarks_128x128.png
* icons-misc\BOOKMARKS\bookmarks_144x144.png
* icons-misc\BOOKMARKS\bookmarks_256x256.png
* icons-misc\BOOKMARKS\bookmarks_32x32.png
* icons-misc\BOOKMARKS\bookmarks_48x48.png
* icons-misc\BOOKMARKS\bookmarks_512x512.png
* icons-misc\BOOKMARKS\bookmarks_64x64.png
* icons-misc\BOOKMARKS\bookmarks_72x72.png
* icons-misc\BOOKS\
* icons-misc\BOOKS\books.cdr
* icons-misc\BOOKS\books_1024x1024.png
* icons-misc\BOOKS\books_114x114.png
* icons-misc\BOOKS\books_128x128.png
* icons-misc\BOOKS\books_144x144.png
* icons-misc\BOOKS\books_256x256.png
* icons-misc\BOOKS\books_32x32.png
* icons-misc\BOOKS\books_48x48.png
* icons-misc\BOOKS\books_512x512.png
* icons-misc\BOOKS\books_64x64.png
* icons-misc\BOOKS\books_72x72.png
* icons-misc\PDF\
* icons-misc\PDF\PDF.cdr
* icons-misc\PDF\PDF_1024x1024.png
* icons-misc\PDF\PDF_114x144.png
* icons-misc\PDF\PDF_128x128.png
* icons-misc\PDF\PDF_144x144.png
* icons-misc\PDF\PDF_256x256.png
* icons-misc\PDF\PDF_32x32.png
* icons-misc\PDF\PDF_48x48.png
* icons-misc\PDF\PDF_512x512.png
* icons-misc\PDF\PDF_64x64.png
* icons-misc\PDF\PDF_72x72.png
* icons-misc\SCAN\
* icons-misc\SCAN\Thumbs.db
* icons-misc\SCAN\scan.cdr
* icons-misc\SCAN\scan_1024x1024.png
* icons-misc\SCAN\scan_114x114.png
* icons-misc\SCAN\scan_128x128.png
* icons-misc\SCAN\scan_144x144.png
* icons-misc\SCAN\scan_256x256.png
* icons-misc\SCAN\scan_32x32.png
* icons-misc\SCAN\scan_48x48.png
* icons-misc\SCAN\scan_512x512.png
* icons-misc\SCAN\scan_64x64.png
* icons-misc\SCAN\scan_72x72.png
* icons-misc\WI-FI\
* icons-misc\WI-FI\wi-fi.cdr
* icons-misc\WI-FI\wi-fi_1024x1024.png
* icons-misc\WI-FI\wi-fi_114x144.png
* icons-misc\WI-FI\wi-fi_128x128.png
* icons-misc\WI-FI\wi-fi_144x144.png
* icons-misc\WI-FI\wi-fi_256x256.png
* icons-misc\WI-FI\wi-fi_32x32.png
* icons-misc\WI-FI\wi-fi_48x48.png
* icons-misc\WI-FI\wi-fi_512x512.png
* icons-misc\WI-FI\wi-fi_64x64.png
* icons-misc\WI-FI\wi-fi_72x72.png
* icons-misc\litres\
* icons-misc\litres\Litres.cdr
* icons-misc\litres\Litres_1024x1024.png
* icons-misc\litres\Litres_114x114.png
* icons-misc\litres\Litres_128x128.png
* icons-misc\litres\Litres_144x144.png
* icons-misc\litres\Litres_256x256.png
* icons-misc\litres\Litres_32x32_1.png
* icons-misc\litres\Litres_32x32_2.png
* icons-misc\litres\Litres_48x48.png
* icons-misc\litres\Litres_512x512.png
* icons-misc\litres\Litres_64x64.png
* icons-misc\litres\Litres_72x72.png
* icons-scalable\
* icons-scalable\FBReader.svg
* icons-scalable\Library Icons.psd
* icons-scalable\basket.psd
* icons-scalable\basketBLUE.psd
* ideas.mss
* obsolete\
* obsolete\build.xml
* obsolete\external\
* obsolete\external\ExternalProgramFormatPlugin.java
* obsolete\external\FBReader.Opener.java
* obsolete\external\Formats.java
* obsolete\help\
* obsolete\help\MiniHelp.be.fb2
* obsolete\help\MiniHelp.ca.fb2
* obsolete\help\MiniHelp.da.fb2
* obsolete\help\MiniHelp.de.fb2
* obsolete\help\MiniHelp.el.fb2
* obsolete\help\MiniHelp.es.fb2
* obsolete\help\MiniHelp.eu.fb2
* obsolete\help\MiniHelp.fr.fb2
* obsolete\help\MiniHelp.gl.fb2
* obsolete\help\MiniHelp.hu.fb2
* obsolete\help\MiniHelp.it.fb2
* obsolete\help\MiniHelp.ja.fb2
* obsolete\help\MiniHelp.ka.fb2
* obsolete\help\MiniHelp.nb.fb2
* obsolete\help\MiniHelp.nl.fb2
* obsolete\help\MiniHelp.pl.fb2
* obsolete\help\MiniHelp.pt.fb2
* obsolete\help\MiniHelp.ro.fb2
* obsolete\help\MiniHelp.sr.fb2
* obsolete\help\MiniHelp.th.fb2
* obsolete\help\MiniHelp.tr.fb2
* obsolete\help\MiniHelp.uk.fb2
* obsolete\help\MiniHelp.vi.fb2
* obsolete\help\MiniHelp.zh.fb2
* obsolete\help\MiniHelp.zh_TW.fb2
* obsolete\icons\
* obsolete\icons\application\
* obsolete\icons\application\48x48.png
* obsolete\icons\application\64x64.png
* obsolete\icons\booktree\
* obsolete\icons\booktree\tree-bookinfo.png
* obsolete\icons\booktree\tree-remove.png
* obsolete\icons\booktree\tree-removetag.png
* obsolete\icons\booktree\tree-taginfo.png
* obsolete\icons\toolbar\
* obsolete\icons\toolbar\addBook.png
* obsolete\icons\toolbar\bookInfo.png
* obsolete\icons\toolbar\findNext.png
* obsolete\icons\toolbar\findPrevious.png
* obsolete\icons\toolbar\gotoHome.png
* obsolete\icons\toolbar\preferences.png
* obsolete\icons\toolbar\redo.png
* obsolete\icons\toolbar\rotate.png
* obsolete\icons\toolbar\search.png
* obsolete\icons\toolbar\showHelp.png
* obsolete\icons\toolbar\showLibrary.png
* obsolete\icons\toolbar\showRecent.png
* obsolete\icons\toolbar\toc.png
* obsolete\icons\toolbar\undo.png
* obsolete\j2me\
* obsolete\j2me\FBReaderJ.jad
* obsolete\j2me\build.xml
* obsolete\j2me\data\
* obsolete\j2me\data\FBReaderJ.png
* obsolete\j2me\data\default\
* obsolete\j2me\data\default\keymap.xml
* obsolete\j2me\data\default\menubar.xml
* obsolete\j2me\data\default\styles.xml
* obsolete\j2me\manifest.mf
* obsolete\j2me\src\
* obsolete\j2me\src\java\
* obsolete\j2me\src\java\util\
* obsolete\j2me\src\java\util\zip\
* obsolete\j2me\src\java\util\zip\ZipEntry.java
* obsolete\j2me\src\java\util\zip\ZipException.java
* obsolete\j2me\src\java\util\zip\ZipFile.java
* obsolete\j2me\src\java\util\zip\ZipInputStream.java
* obsolete\j2me\src\org\
* obsolete\j2me\src\org\zlibrary\
* obsolete\j2me\src\org\zlibrary\core\
* obsolete\j2me\src\org\zlibrary\core\util\
* obsolete\j2me\src\org\zlibrary\core\util\ArrayList.java
* obsolete\j2me\src\org\zlibrary\core\util\Collections.java
* obsolete\j2me\src\org\zlibrary\core\util\Comparator.java
* obsolete\j2me\src\org\zlibrary\core\util\File.java
* obsolete\j2me\src\org\zlibrary\core\util\HashMap.java
* obsolete\j2me\src\org\zlibrary\core\util\Locale.java
* obsolete\j2me\src\org\zlibrary\ui\
* obsolete\j2me\src\org\zlibrary\ui\j2me\
* obsolete\j2me\src\org\zlibrary\ui\j2me\application\
* obsolete\j2me\src\org\zlibrary\ui\j2me\application\ZLJ2MEApplicationWindow.java
* obsolete\j2me\src\org\zlibrary\ui\j2me\config\
* obsolete\j2me\src\org\zlibrary\ui\j2me\config\ZLJ2MEConfig.java
* obsolete\j2me\src\org\zlibrary\ui\j2me\config\ZLJ2MEConfigManager.java
* obsolete\j2me\src\org\zlibrary\ui\j2me\image\
* obsolete\j2me\src\org\zlibrary\ui\j2me\image\ZLJ2MEImageData.java
* obsolete\j2me\src\org\zlibrary\ui\j2me\image\ZLJ2MEImageManager.java
* obsolete\j2me\src\org\zlibrary\ui\j2me\library\
* obsolete\j2me\src\org\zlibrary\ui\j2me\library\ZLJ2MELibrary.java
* obsolete\j2me\src\org\zlibrary\ui\j2me\library\ZLMIDlet.java
* obsolete\j2me\src\org\zlibrary\ui\j2me\view\
* obsolete\j2me\src\org\zlibrary\ui\j2me\view\ZLCanvas.java
* obsolete\j2me\src\org\zlibrary\ui\j2me\view\ZLJ2MEPaintContext.java
* obsolete\j2me\src\org\zlibrary\ui\j2me\view\ZLJ2MEViewWidget.java
* obsolete\lib\
* obsolete\lib\antenna-bin-1.0.0.jar
* obsolete\lib\junit.jar
* obsolete\lib\proguard.jar
* obsolete\manifest.mf
* obsolete\swing\
* obsolete\swing\data\
* obsolete\swing\data\default\
* obsolete\swing\data\default\keymap.xml
* obsolete\swing\data\default\styles.xml
* obsolete\swing\data\default\toolbar.xml
* obsolete\swing\icons\
* obsolete\swing\icons\filetree\
* obsolete\swing\icons\filetree\fb2.png
* obsolete\swing\icons\filetree\folder.png
* obsolete\swing\icons\filetree\html.png
* obsolete\swing\icons\filetree\mobipocket.png
* obsolete\swing\icons\filetree\oeb.png
* obsolete\swing\icons\filetree\openreader.png
* obsolete\swing\icons\filetree\palm.png
* obsolete\swing\icons\filetree\plucker.png
* obsolete\swing\icons\filetree\rtf.png
* obsolete\swing\icons\filetree\tcr.png
* obsolete\swing\icons\filetree\unknown.png
* obsolete\swing\icons\filetree\upfolder.png
* obsolete\swing\icons\filetree\weasel.png
* obsolete\swing\icons\filetree\zipfolder.png
* obsolete\swing\src\
* obsolete\swing\src\org\
* obsolete\swing\src\org\geometerplus\
* obsolete\swing\src\org\geometerplus\zlibrary\
* obsolete\swing\src\org\geometerplus\zlibrary\core\
* obsolete\swing\src\org\geometerplus\zlibrary\core\xmlconfig\
* obsolete\swing\src\org\geometerplus\zlibrary\core\xmlconfig\ZLConfigImpl.java
* obsolete\swing\src\org\geometerplus\zlibrary\core\xmlconfig\ZLConfigReader.java
* obsolete\swing\src\org\geometerplus\zlibrary\core\xmlconfig\ZLConfigWriter.java
* obsolete\swing\src\org\geometerplus\zlibrary\core\xmlconfig\ZLDeletedValuesSet.java
* obsolete\swing\src\org\geometerplus\zlibrary\core\xmlconfig\ZLDeltaConfig.java
* obsolete\swing\src\org\geometerplus\zlibrary\core\xmlconfig\ZLGroup.java
* obsolete\swing\src\org\geometerplus\zlibrary\core\xmlconfig\ZLOptionID.java
* obsolete\swing\src\org\geometerplus\zlibrary\core\xmlconfig\ZLOptionInfo.java
* obsolete\swing\src\org\geometerplus\zlibrary\core\xmlconfig\ZLReader.java
* obsolete\swing\src\org\geometerplus\zlibrary\core\xmlconfig\ZLSimpleConfig.java
* obsolete\swing\src\org\geometerplus\zlibrary\core\xmlconfig\ZLWriter.java
* obsolete\swing\src\org\geometerplus\zlibrary\core\xmlconfig\ZLXMLConfigManager.java
* obsolete\swing\src\org\geometerplus\zlibrary\ui\
* obsolete\swing\src\org\geometerplus\zlibrary\ui\swing\
* obsolete\swing\src\org\geometerplus\zlibrary\ui\swing\application\
* obsolete\swing\src\org\geometerplus\zlibrary\ui\swing\application\ZLSwingApplicationWindow.java
* obsolete\swing\src\org\geometerplus\zlibrary\ui\swing\dialogs\
* obsolete\swing\src\org\geometerplus\zlibrary\ui\swing\dialogs\ZLBoolean3OptionView.java
* obsolete\swing\src\org\geometerplus\zlibrary\ui\swing\dialogs\ZLBooleanOptionView.java
* obsolete\swing\src\org\geometerplus\zlibrary\ui\swing\dialogs\ZLChoiceOptionView.java
* obsolete\swing\src\org\geometerplus\zlibrary\ui\swing\dialogs\ZLColorOptionView.java
* obsolete\swing\src\org\geometerplus\zlibrary\ui\swing\dialogs\ZLComboOptionView.java
* obsolete\swing\src\org\geometerplus\zlibrary\ui\swing\dialogs\ZLKeyOptionView.java
* obsolete\swing\src\org\geometerplus\zlibrary\ui\swing\dialogs\ZLSpinOptionView.java
* obsolete\swing\src\org\geometerplus\zlibrary\ui\swing\dialogs\ZLStringOptionView.java
* obsolete\swing\src\org\geometerplus\zlibrary\ui\swing\dialogs\ZLSwingDialog.java
* obsolete\swing\src\org\geometerplus\zlibrary\ui\swing\dialogs\ZLSwingDialogContent.java
* obsolete\swing\src\org\geometerplus\zlibrary\ui\swing\dialogs\ZLSwingDialogManager.java
* obsolete\swing\src\org\geometerplus\zlibrary\ui\swing\dialogs\ZLSwingOptionView.java
* obsolete\swing\src\org\geometerplus\zlibrary\ui\swing\dialogs\ZLSwingOptionsDialog.java
* obsolete\swing\src\org\geometerplus\zlibrary\ui\swing\dialogs\ZLSwingSelectionDialog.java
* obsolete\swing\src\org\geometerplus\zlibrary\ui\swing\image\
* obsolete\swing\src\org\geometerplus\zlibrary\ui\swing\image\ZLSwingImageData.java
* obsolete\swing\src\org\geometerplus\zlibrary\ui\swing\image\ZLSwingImageManager.java
* obsolete\swing\src\org\geometerplus\zlibrary\ui\swing\library\
* obsolete\swing\src\org\geometerplus\zlibrary\ui\swing\library\Main.java
* obsolete\swing\src\org\geometerplus\zlibrary\ui\swing\library\ZLSwingLibrary.java
* obsolete\swing\src\org\geometerplus\zlibrary\ui\swing\util\
* obsolete\swing\src\org\geometerplus\zlibrary\ui\swing\util\ZLSwingIconUtil.java
* obsolete\swing\src\org\geometerplus\zlibrary\ui\swing\view\
* obsolete\swing\src\org\geometerplus\zlibrary\ui\swing\view\ZLSwingPaintContext.java
* obsolete\swing\src\org\geometerplus\zlibrary\ui\swing\view\ZLSwingViewWidget.java
* obsolete\test\
* obsolete\test\data\
* obsolete\test\data\fb2\
* obsolete\test\data\fb2\annotation.fb2
* obsolete\test\data\fb2\annotation_before.fb2
* obsolete\test\data\fb2\annotation_before_exp.txt
* obsolete\test\data\fb2\annotation_exp.txt
* obsolete\test\data\fb2\cite.fb2
* obsolete\test\data\fb2\cite_exp.txt
* obsolete\test\data\fb2\date.fb2
* obsolete\test\data\fb2\date_exp.txt
* obsolete\test\data\fb2\empty_line.fb2
* obsolete\test\data\fb2\empty_line_exp.txt
* obsolete\test\data\fb2\epigraph.fb2
* obsolete\test\data\fb2\epigraph_exp.txt
* obsolete\test\data\fb2\ext_hyperlink.fb2
* obsolete\test\data\fb2\ext_hyperlink_exp.txt
* obsolete\test\data\fb2\filesystem\
* obsolete\test\data\fb2\filesystem\directory\
* obsolete\test\data\fb2\filesystem\directory\testbookZip.zip
* obsolete\test\data\fb2\filesystem\directory\testfb2book.fb2
* obsolete\test\data\fb2\filesystem\testbookZip.zip
* obsolete\test\data\fb2\filesystem\testfb2book.fb2
* obsolete\test\data\fb2\footnote.fb2
* obsolete\test\data\fb2\footnote1.fb2
* obsolete\test\data\fb2\footnote_exp.txt
* obsolete\test\data\fb2\image.fb2
* obsolete\test\data\fb2\image_exp.txt
* obsolete\test\data\fb2\karenina.fb2
* obsolete\test\data\fb2\karenina_exp.txt
* obsolete\test\data\fb2\one_note.fb2
* obsolete\test\data\fb2\one_note_exp.txt
* obsolete\test\data\fb2\poem.fb2
* obsolete\test\data\fb2\poem_exp.txt
* obsolete\test\data\fb2\poem_title.fb2
* obsolete\test\data\fb2\poem_title_exp.txt
* obsolete\test\data\fb2\section.fb2
* obsolete\test\data\fb2\section_exp.txt
* obsolete\test\data\fb2\section_title.fb2
* obsolete\test\data\fb2\section_title_exp.txt
* obsolete\test\data\fb2\simple_notes.fb2
* obsolete\test\data\fb2\stanza.fb2
* obsolete\test\data\fb2\stanza_exp.txt
* obsolete\test\data\fb2\subtitle.fb2
* obsolete\test\data\fb2\subtitle_exp.txt
* obsolete\test\data\fb2\test1.fb2
* obsolete\test\data\fb2\test1_exp.txt
* obsolete\test\data\fb2\test2.fb2
* obsolete\test\data\fb2\test2_exp.txt
* obsolete\test\data\fb2\test3.fb2
* obsolete\test\data\fb2\test3_exp.txt
* obsolete\test\data\fb2\test4.fb2
* obsolete\test\data\fb2\test4_exp.txt
* obsolete\test\data\fb2\test5.fb2
* obsolete\test\data\fb2\test5_exp.txt
* obsolete\test\data\fb2\test6.fb2
* obsolete\test\data\fb2\test6_exp.txt
* obsolete\test\data\fb2\test7.fb2
* obsolete\test\data\fb2\test7_exp.txt
* obsolete\test\data\fb2\test8.fb2
* obsolete\test\data\fb2\test8_exp.txt
* obsolete\test\data\fb2\text_author.fb2
* obsolete\test\data\fb2\text_author_exp.txt
* obsolete\test\data\fb2\title.fb2
* obsolete\test\data\fb2\title_exp.txt
* obsolete\test\data\fb2\tree1.fb2
* obsolete\test\data\fb2\tree1_exp.txt
* obsolete\test\data\fb2\tree2.fb2
* obsolete\test\data\fb2\tree2_exp.txt
* obsolete\test\data\fb2\tree2sections.fb2
* obsolete\test\data\fb2\tree2sections_exp.txt
* obsolete\test\data\fb2\verse.fb2
* obsolete\test\data\fb2\verse_exp.txt
* obsolete\test\data\fb2\whiteguard.fb2
* obsolete\test\data\fb2\whiteguard_exp.txt
* obsolete\test\data\resources\
* obsolete\test\data\resources\application\
* obsolete\test\data\resources\application\en.xml
* obsolete\test\data\resources\application\fi.xml
* obsolete\test\data\resources\application\fr.xml
* obsolete\test\data\resources\application\it.xml
* obsolete\test\data\resources\application\ru.xml
* obsolete\test\data\resources\application\sv.xml
* obsolete\test\data\resources\application\uk.xml
* obsolete\test\data\resources\zlibrary\
* obsolete\test\data\resources\zlibrary\en.xml
* obsolete\test\data\resources\zlibrary\fi.xml
* obsolete\test\data\resources\zlibrary\fr.xml
* obsolete\test\data\resources\zlibrary\it.xml
* obsolete\test\data\resources\zlibrary\ru.xml
* obsolete\test\data\resources\zlibrary\sv.xml
* obsolete\test\data\resources\zlibrary\uk.xml
* obsolete\test\org\
* obsolete\test\org\test\
* obsolete\test\org\test\fbreader\
* obsolete\test\org\test\fbreader\collection\
* obsolete\test\org\test\fbreader\collection\AllTests.java
* obsolete\test\org\test\fbreader\collection\TestBookCollection.java
* obsolete\test\org\test\fbreader\collection\TestBookList.java
* obsolete\test\org\test\fbreader\formats\
* obsolete\test\org\test\fbreader\formats\fb2\
* obsolete\test\org\test\fbreader\formats\fb2\AllTests.java
* obsolete\test\org\test\fbreader\formats\fb2\TestFB2Reader.java
* obsolete\test\org\test\zlibrary\
* obsolete\test\org\test\zlibrary\core\
* obsolete\test\org\test\zlibrary\core\resources\
* obsolete\test\org\test\zlibrary\core\resources\AllTests.java
* obsolete\test\org\test\zlibrary\core\resources\TestResources.java
* obsolete\test\org\test\zlibrary\description\
* obsolete\test\org\test\zlibrary\description\AllTests.java
* obsolete\test\org\test\zlibrary\description\TestDescriptionBook.java
* obsolete\test\org\test\zlibrary\filesystem\
* obsolete\test\org\test\zlibrary\filesystem\TestALL.java
* obsolete\test\org\test\zlibrary\filesystem\TestZLDir.java
* obsolete\test\org\test\zlibrary\filesystem\TestZLFSManager.java
* obsolete\test\org\test\zlibrary\filesystem\TestZLFile.java
* obsolete\test\org\test\zlibrary\hyphenation\
* obsolete\test\org\test\zlibrary\hyphenation\TestAll.java
* obsolete\test\org\test\zlibrary\hyphenation\TestTextTeXHyphenationPattern.java
* obsolete\test\org\test\zlibrary\hyphenation\TestTextTeXHyphenator.java
* obsolete\test\org\test\zlibrary\model\
* obsolete\test\org\test\zlibrary\model\ModelDumper.java
* obsolete\test\org\test\zlibrary\model\TestAll.java
* obsolete\test\org\test\zlibrary\model\TestTextControlEntry.java
* obsolete\test\org\test\zlibrary\model\TestTextEntry.java
* obsolete\test\org\test\zlibrary\model\TestTextParagraph.java
* obsolete\test\org\test\zlibrary\model\TestTreeModel.java
* obsolete\test\org\test\zlibrary\model\TestTreeParagraph.java
* obsolete\test\org\test\zlibrary\model\TestZLTextModel.java
* obsolete\test\org\test\zlibrary\options\
* obsolete\test\org\test\zlibrary\options\ConfigIOTests.java
* obsolete\test\org\test\zlibrary\options\TestAll.java
* obsolete\test\org\test\zlibrary\options\UtilTests.java
* obsolete\test\org\test\zlibrary\options\ZLBoolean3OptionTests1.java
* obsolete\test\org\test\zlibrary\options\ZLBoolean3OptionTests2.java
* obsolete\test\org\test\zlibrary\options\ZLBooleanOptionTests1.java
* obsolete\test\org\test\zlibrary\options\ZLBooleanOptionTests2.java
* obsolete\test\org\test\zlibrary\options\ZLColorOptionTests1.java
* obsolete\test\org\test\zlibrary\options\ZLColorOptionTests2.java
* obsolete\test\org\test\zlibrary\options\ZLConfigWriterTests.java
* obsolete\test\org\test\zlibrary\options\ZLIntegerOptionTests1.java
* obsolete\test\org\test\zlibrary\options\ZLIntegerOptionTests2.java
* obsolete\test\org\test\zlibrary\options\ZLIntegerRangeOptionTests1.java
* obsolete\test\org\test\zlibrary\options\ZLIntegerRangeOptionTests2.java
* obsolete\test\org\test\zlibrary\options\ZLOptionTests.java
* obsolete\test\org\test\zlibrary\options\ZLStringOptionTests1.java
* obsolete\test\org\test\zlibrary\options\ZLStringOptionTests2.java
* obsolete\test\org\test\zlibrary\options\examples\
* obsolete\test\org\test\zlibrary\options\examples\options.xml
* proguard.cfg
* scripts\
* scripts\buildAll.sh
* scripts\changeCopyright.pl
* scripts\copyright
* scripts\mergeAll.sh
* scripts\packageTool.sh
* scripts\resources\
* scripts\resources\clean.xslt
* scripts\resources\repair.py
* scripts\resources\stats.sh
* scripts\resources\synchronize.sh
* scripts\resources\update.sh
* scripts\set_tags.sh
* third-party\
* third-party\AmbilWarna\
* third-party\AmbilWarna\.project
* third-party\AmbilWarna\AmbilWarna.iml
* third-party\AmbilWarna\AndroidManifest.xml
* third-party\AmbilWarna\build.xml
* third-party\AmbilWarna\project.properties
* third-party\AmbilWarna\res\
* third-party\AmbilWarna\res\drawable-hdpi\
* third-party\AmbilWarna\res\drawable-hdpi\ambilwarna_arrow_down.png
* third-party\AmbilWarna\res\drawable-hdpi\ambilwarna_arrow_right.png
* third-party\AmbilWarna\res\drawable-hdpi\ambilwarna_cursor.png
* third-party\AmbilWarna\res\drawable-hdpi\ambilwarna_target.png
* third-party\AmbilWarna\res\drawable-ldpi\
* third-party\AmbilWarna\res\drawable-ldpi\ambilwarna_arrow_down.png
* third-party\AmbilWarna\res\drawable-ldpi\ambilwarna_arrow_right.png
* third-party\AmbilWarna\res\drawable-ldpi\ambilwarna_cursor.png
* third-party\AmbilWarna\res\drawable-ldpi\ambilwarna_target.png
* third-party\AmbilWarna\res\drawable-xhdpi\
* third-party\AmbilWarna\res\drawable-xhdpi\ambilwarna_arrow_down.png
* third-party\AmbilWarna\res\drawable-xhdpi\ambilwarna_arrow_right.png
* third-party\AmbilWarna\res\drawable-xhdpi\ambilwarna_cursor.png
* third-party\AmbilWarna\res\drawable-xhdpi\ambilwarna_target.png
* third-party\AmbilWarna\res\drawable\
* third-party\AmbilWarna\res\drawable\ambilwarna_arrow_down.png
* third-party\AmbilWarna\res\drawable\ambilwarna_arrow_right.png
* third-party\AmbilWarna\res\drawable\ambilwarna_cursor.png
* third-party\AmbilWarna\res\drawable\ambilwarna_hue.png
* third-party\AmbilWarna\res\drawable\ambilwarna_target.png
* third-party\AmbilWarna\res\layout-land\
* third-party\AmbilWarna\res\layout-land\ambilwarna_dialog.xml
* third-party\AmbilWarna\res\layout\
* third-party\AmbilWarna\res\layout\ambilwarna_dialog.xml
* third-party\AmbilWarna\res\layout\ambilwarna_pref_widget.xml
* third-party\AmbilWarna\res\values-land\
* third-party\AmbilWarna\res\values-land\dimen.xml
* third-party\AmbilWarna\res\values-xlarge-land\
* third-party\AmbilWarna\res\values-xlarge-land\dimen.xml
* third-party\AmbilWarna\res\values\
* third-party\AmbilWarna\res\values\dimen.xml
* third-party\AmbilWarna\src\
* third-party\AmbilWarna\src\yuku\
* third-party\AmbilWarna\src\yuku\ambilwarna\
* third-party\AmbilWarna\src\yuku\ambilwarna\AmbilWarnaDialog.java
* third-party\AmbilWarna\src\yuku\ambilwarna\AmbilWarnaKotak.java
* third-party\AmbilWarna\src\yuku\ambilwarna\widget\
* third-party\AmbilWarna\src\yuku\ambilwarna\widget\AmbilWarnaPrefWidgetView.java
* third-party\android-filechooser\
* third-party\android-filechooser\LICENSE
* third-party\android-filechooser\NOTICE
* third-party\android-filechooser\code\
* third-party\android-filechooser\code\AndroidManifest.xml
* third-party\android-filechooser\code\build.xml
* third-party\android-filechooser\code\proguard-project.txt
* third-party\android-filechooser\code\proguard.cfg
* third-party\android-filechooser\code\project.properties
* third-party\android-filechooser\code\res\
* third-party\android-filechooser\code\res\drawable-hdpi-v11\
* third-party\android-filechooser\code\res\drawable-hdpi-v11\afc_ic_menu_gridview.png
* third-party\android-filechooser\code\res\drawable-hdpi-v11\afc_ic_menu_home.png
* third-party\android-filechooser\code\res\drawable-hdpi-v11\afc_ic_menu_listview.png
* third-party\android-filechooser\code\res\drawable-hdpi-v11\afc_ic_menu_reload.png
* third-party\android-filechooser\code\res\drawable-hdpi-v11\afc_ic_menu_sort_by_date_asc.png
* third-party\android-filechooser\code\res\drawable-hdpi-v11\afc_ic_menu_sort_by_date_desc.png
* third-party\android-filechooser\code\res\drawable-hdpi-v11\afc_ic_menu_sort_by_name_asc.png
* third-party\android-filechooser\code\res\drawable-hdpi-v11\afc_ic_menu_sort_by_name_desc.png
* third-party\android-filechooser\code\res\drawable-hdpi-v11\afc_ic_menu_sort_by_size_asc.png
* third-party\android-filechooser\code\res\drawable-hdpi-v11\afc_ic_menu_sort_by_size_desc.png
* third-party\android-filechooser\code\res\drawable-hdpi\
* third-party\android-filechooser\code\res\drawable-hdpi\afc_button_create_folder.png
* third-party\android-filechooser\code\res\drawable-hdpi\afc_button_create_folder_disabled.png
* third-party\android-filechooser\code\res\drawable-hdpi\afc_button_create_folder_pressed.png
* third-party\android-filechooser\code\res\drawable-hdpi\afc_button_folders_view_grid.png
* third-party\android-filechooser\code\res\drawable-hdpi\afc_button_folders_view_grid_disabled.png
* third-party\android-filechooser\code\res\drawable-hdpi\afc_button_folders_view_grid_pressed.png
* third-party\android-filechooser\code\res\drawable-hdpi\afc_button_folders_view_list.png
* third-party\android-filechooser\code\res\drawable-hdpi\afc_button_folders_view_list_disabled.png
* third-party\android-filechooser\code\res\drawable-hdpi\afc_button_folders_view_list_pressed.png
* third-party\android-filechooser\code\res\drawable-hdpi\afc_button_navi_left.png
* third-party\android-filechooser\code\res\drawable-hdpi\afc_button_navi_left_disabled.png
* third-party\android-filechooser\code\res\drawable-hdpi\afc_button_navi_left_pressed.png
* third-party\android-filechooser\code\res\drawable-hdpi\afc_button_navi_right.png
* third-party\android-filechooser\code\res\drawable-hdpi\afc_button_navi_right_disabled.png
* third-party\android-filechooser\code\res\drawable-hdpi\afc_button_navi_right_pressed.png
* third-party\android-filechooser\code\res\drawable-hdpi\afc_button_ok_saveas.png
* third-party\android-filechooser\code\res\drawable-hdpi\afc_button_ok_saveas_pressed.png
* third-party\android-filechooser\code\res\drawable-hdpi\afc_button_sort_as.png
* third-party\android-filechooser\code\res\drawable-hdpi\afc_button_sort_as_disabled.png
* third-party\android-filechooser\code\res\drawable-hdpi\afc_button_sort_as_pressed.png
* third-party\android-filechooser\code\res\drawable-hdpi\afc_button_sort_de.png
* third-party\android-filechooser\code\res\drawable-hdpi\afc_button_sort_de_disabled.png
* third-party\android-filechooser\code\res\drawable-hdpi\afc_button_sort_de_pressed.png
* third-party\android-filechooser\code\res\drawable-hdpi\afc_context_menu_item_divider.png
* third-party\android-filechooser\code\res\drawable-hdpi\afc_file.png
* third-party\android-filechooser\code\res\drawable-hdpi\afc_file_audio.png
* third-party\android-filechooser\code\res\drawable-hdpi\afc_file_compressed.png
* third-party\android-filechooser\code\res\drawable-hdpi\afc_file_disabled.png
* third-party\android-filechooser\code\res\drawable-hdpi\afc_file_image.png
* third-party\android-filechooser\code\res\drawable-hdpi\afc_file_plain_text.png
* third-party\android-filechooser\code\res\drawable-hdpi\afc_file_video.png
* third-party\android-filechooser\code\res\drawable-hdpi\afc_folder.png
* third-party\android-filechooser\code\res\drawable-hdpi\afc_folder_disabled.png
* third-party\android-filechooser\code\res\drawable-hdpi\afc_folder_locked.png
* third-party\android-filechooser\code\res\drawable-hdpi\afc_ic_menu_gridview.png
* third-party\android-filechooser\code\res\drawable-hdpi\afc_ic_menu_home.png
* third-party\android-filechooser\code\res\drawable-hdpi\afc_ic_menu_listview.png
* third-party\android-filechooser\code\res\drawable-hdpi\afc_ic_menu_reload.png
* third-party\android-filechooser\code\res\drawable-hdpi\afc_ic_menu_sort_by_date_asc.png
* third-party\android-filechooser\code\res\drawable-hdpi\afc_ic_menu_sort_by_date_desc.png
* third-party\android-filechooser\code\res\drawable-hdpi\afc_ic_menu_sort_by_name_asc.png
* third-party\android-filechooser\code\res\drawable-hdpi\afc_ic_menu_sort_by_name_desc.png
* third-party\android-filechooser\code\res\drawable-hdpi\afc_ic_menu_sort_by_size_asc.png
* third-party\android-filechooser\code\res\drawable-hdpi\afc_ic_menu_sort_by_size_desc.png
* third-party\android-filechooser\code\res\drawable-ldpi-v11\
* third-party\android-filechooser\code\res\drawable-ldpi-v11\afc_ic_menu_gridview.png
* third-party\android-filechooser\code\res\drawable-ldpi-v11\afc_ic_menu_home.png
* third-party\android-filechooser\code\res\drawable-ldpi-v11\afc_ic_menu_listview.png
* third-party\android-filechooser\code\res\drawable-ldpi-v11\afc_ic_menu_reload.png
* third-party\android-filechooser\code\res\drawable-ldpi-v11\afc_ic_menu_sort_by_date_asc.png
* third-party\android-filechooser\code\res\drawable-ldpi-v11\afc_ic_menu_sort_by_date_desc.png
* third-party\android-filechooser\code\res\drawable-ldpi-v11\afc_ic_menu_sort_by_name_asc.png
* third-party\android-filechooser\code\res\drawable-ldpi-v11\afc_ic_menu_sort_by_name_desc.png
* third-party\android-filechooser\code\res\drawable-ldpi-v11\afc_ic_menu_sort_by_size_asc.png
* third-party\android-filechooser\code\res\drawable-ldpi-v11\afc_ic_menu_sort_by_size_desc.png
* third-party\android-filechooser\code\res\drawable-ldpi\
* third-party\android-filechooser\code\res\drawable-ldpi\afc_button_create_folder.png
* third-party\android-filechooser\code\res\drawable-ldpi\afc_button_create_folder_disabled.png
* third-party\android-filechooser\code\res\drawable-ldpi\afc_button_create_folder_pressed.png
* third-party\android-filechooser\code\res\drawable-ldpi\afc_button_folders_view_grid.png
* third-party\android-filechooser\code\res\drawable-ldpi\afc_button_folders_view_grid_disabled.png
* third-party\android-filechooser\code\res\drawable-ldpi\afc_button_folders_view_grid_pressed.png
* third-party\android-filechooser\code\res\drawable-ldpi\afc_button_folders_view_list.png
* third-party\android-filechooser\code\res\drawable-ldpi\afc_button_folders_view_list_disabled.png
* third-party\android-filechooser\code\res\drawable-ldpi\afc_button_folders_view_list_pressed.png
* third-party\android-filechooser\code\res\drawable-ldpi\afc_button_navi_left.png
* third-party\android-filechooser\code\res\drawable-ldpi\afc_button_navi_left_disabled.png
* third-party\android-filechooser\code\res\drawable-ldpi\afc_button_navi_left_pressed.png
* third-party\android-filechooser\code\res\drawable-ldpi\afc_button_navi_right.png
* third-party\android-filechooser\code\res\drawable-ldpi\afc_button_navi_right_disabled.png
* third-party\android-filechooser\code\res\drawable-ldpi\afc_button_navi_right_pressed.png
* third-party\android-filechooser\code\res\drawable-ldpi\afc_button_ok_saveas.png
* third-party\android-filechooser\code\res\drawable-ldpi\afc_button_ok_saveas_pressed.png
* third-party\android-filechooser\code\res\drawable-ldpi\afc_context_menu_item_divider.png
* third-party\android-filechooser\code\res\drawable-ldpi\afc_file.png
* third-party\android-filechooser\code\res\drawable-ldpi\afc_file_audio.png
* third-party\android-filechooser\code\res\drawable-ldpi\afc_file_compressed.png
* third-party\android-filechooser\code\res\drawable-ldpi\afc_file_disabled.png
* third-party\android-filechooser\code\res\drawable-ldpi\afc_file_image.png
* third-party\android-filechooser\code\res\drawable-ldpi\afc_file_plain_text.png
* third-party\android-filechooser\code\res\drawable-ldpi\afc_file_video.png
* third-party\android-filechooser\code\res\drawable-ldpi\afc_folder.png
* third-party\android-filechooser\code\res\drawable-ldpi\afc_folder_disabled.png
* third-party\android-filechooser\code\res\drawable-ldpi\afc_folder_locked.png
* third-party\android-filechooser\code\res\drawable-ldpi\afc_ic_menu_gridview.png
* third-party\android-filechooser\code\res\drawable-ldpi\afc_ic_menu_home.png
* third-party\android-filechooser\code\res\drawable-ldpi\afc_ic_menu_listview.png
* third-party\android-filechooser\code\res\drawable-ldpi\afc_ic_menu_reload.png
* third-party\android-filechooser\code\res\drawable-ldpi\afc_ic_menu_sort_by_date_asc.png
* third-party\android-filechooser\code\res\drawable-ldpi\afc_ic_menu_sort_by_date_desc.png
* third-party\android-filechooser\code\res\drawable-ldpi\afc_ic_menu_sort_by_name_asc.png
* third-party\android-filechooser\code\res\drawable-ldpi\afc_ic_menu_sort_by_name_desc.png
* third-party\android-filechooser\code\res\drawable-ldpi\afc_ic_menu_sort_by_size_asc.png
* third-party\android-filechooser\code\res\drawable-ldpi\afc_ic_menu_sort_by_size_desc.png
* third-party\android-filechooser\code\res\drawable-mdpi-v11\
* third-party\android-filechooser\code\res\drawable-mdpi-v11\afc_ic_menu_gridview.png
* third-party\android-filechooser\code\res\drawable-mdpi-v11\afc_ic_menu_home.png
* third-party\android-filechooser\code\res\drawable-mdpi-v11\afc_ic_menu_listview.png
* third-party\android-filechooser\code\res\drawable-mdpi-v11\afc_ic_menu_reload.png
* third-party\android-filechooser\code\res\drawable-mdpi-v11\afc_ic_menu_sort_by_date_asc.png
* third-party\android-filechooser\code\res\drawable-mdpi-v11\afc_ic_menu_sort_by_date_desc.png
* third-party\android-filechooser\code\res\drawable-mdpi-v11\afc_ic_menu_sort_by_name_asc.png
* third-party\android-filechooser\code\res\drawable-mdpi-v11\afc_ic_menu_sort_by_name_desc.png
* third-party\android-filechooser\code\res\drawable-mdpi-v11\afc_ic_menu_sort_by_size_asc.png
* third-party\android-filechooser\code\res\drawable-mdpi-v11\afc_ic_menu_sort_by_size_desc.png
* third-party\android-filechooser\code\res\drawable-mdpi\
* third-party\android-filechooser\code\res\drawable-mdpi\afc_button_create_folder.png
* third-party\android-filechooser\code\res\drawable-mdpi\afc_button_create_folder_disabled.png
* third-party\android-filechooser\code\res\drawable-mdpi\afc_button_create_folder_pressed.png
* third-party\android-filechooser\code\res\drawable-mdpi\afc_button_folders_view_grid.png
* third-party\android-filechooser\code\res\drawable-mdpi\afc_button_folders_view_grid_disabled.png
* third-party\android-filechooser\code\res\drawable-mdpi\afc_button_folders_view_grid_pressed.png
* third-party\android-filechooser\code\res\drawable-mdpi\afc_button_folders_view_list.png
* third-party\android-filechooser\code\res\drawable-mdpi\afc_button_folders_view_list_disabled.png
* third-party\android-filechooser\code\res\drawable-mdpi\afc_button_folders_view_list_pressed.png
* third-party\android-filechooser\code\res\drawable-mdpi\afc_button_navi_left.png
* third-party\android-filechooser\code\res\drawable-mdpi\afc_button_navi_left_disabled.png
* third-party\android-filechooser\code\res\drawable-mdpi\afc_button_navi_left_pressed.png
* third-party\android-filechooser\code\res\drawable-mdpi\afc_button_navi_right.png
* third-party\android-filechooser\code\res\drawable-mdpi\afc_button_navi_right_disabled.png
* third-party\android-filechooser\code\res\drawable-mdpi\afc_button_navi_right_pressed.png
* third-party\android-filechooser\code\res\drawable-mdpi\afc_button_ok_saveas.png
* third-party\android-filechooser\code\res\drawable-mdpi\afc_button_ok_saveas_pressed.png
* third-party\android-filechooser\code\res\drawable-mdpi\afc_context_menu_item_divider.png
* third-party\android-filechooser\code\res\drawable-mdpi\afc_file.png
* third-party\android-filechooser\code\res\drawable-mdpi\afc_file_audio.png
* third-party\android-filechooser\code\res\drawable-mdpi\afc_file_compressed.png
* third-party\android-filechooser\code\res\drawable-mdpi\afc_file_disabled.png
* third-party\android-filechooser\code\res\drawable-mdpi\afc_file_image.png
* third-party\android-filechooser\code\res\drawable-mdpi\afc_file_plain_text.png
* third-party\android-filechooser\code\res\drawable-mdpi\afc_file_video.png
* third-party\android-filechooser\code\res\drawable-mdpi\afc_folder.png
* third-party\android-filechooser\code\res\drawable-mdpi\afc_folder_disabled.png
* third-party\android-filechooser\code\res\drawable-mdpi\afc_folder_locked.png
* third-party\android-filechooser\code\res\drawable-mdpi\afc_ic_menu_gridview.png
* third-party\android-filechooser\code\res\drawable-mdpi\afc_ic_menu_home.png
* third-party\android-filechooser\code\res\drawable-mdpi\afc_ic_menu_listview.png
* third-party\android-filechooser\code\res\drawable-mdpi\afc_ic_menu_reload.png
* third-party\android-filechooser\code\res\drawable-mdpi\afc_ic_menu_sort_by_date_asc.png
* third-party\android-filechooser\code\res\drawable-mdpi\afc_ic_menu_sort_by_date_desc.png
* third-party\android-filechooser\code\res\drawable-mdpi\afc_ic_menu_sort_by_name_asc.png
* third-party\android-filechooser\code\res\drawable-mdpi\afc_ic_menu_sort_by_name_desc.png
* third-party\android-filechooser\code\res\drawable-mdpi\afc_ic_menu_sort_by_size_asc.png
* third-party\android-filechooser\code\res\drawable-mdpi\afc_ic_menu_sort_by_size_desc.png
* third-party\android-filechooser\code\res\drawable-xhdpi-v11\
* third-party\android-filechooser\code\res\drawable-xhdpi-v11\afc_ic_menu_gridview.png
* third-party\android-filechooser\code\res\drawable-xhdpi-v11\afc_ic_menu_home.png
* third-party\android-filechooser\code\res\drawable-xhdpi-v11\afc_ic_menu_listview.png
* third-party\android-filechooser\code\res\drawable-xhdpi-v11\afc_ic_menu_reload.png
* third-party\android-filechooser\code\res\drawable-xhdpi-v11\afc_ic_menu_sort_by_date_asc.png
* third-party\android-filechooser\code\res\drawable-xhdpi-v11\afc_ic_menu_sort_by_date_desc.png
* third-party\android-filechooser\code\res\drawable-xhdpi-v11\afc_ic_menu_sort_by_name_asc.png
* third-party\android-filechooser\code\res\drawable-xhdpi-v11\afc_ic_menu_sort_by_name_desc.png
* third-party\android-filechooser\code\res\drawable-xhdpi-v11\afc_ic_menu_sort_by_size_asc.png
* third-party\android-filechooser\code\res\drawable-xhdpi-v11\afc_ic_menu_sort_by_size_desc.png
* third-party\android-filechooser\code\res\drawable-xhdpi\
* third-party\android-filechooser\code\res\drawable-xhdpi\afc_button_create_folder.png
* third-party\android-filechooser\code\res\drawable-xhdpi\afc_button_create_folder_disabled.png
* third-party\android-filechooser\code\res\drawable-xhdpi\afc_button_create_folder_pressed.png
* third-party\android-filechooser\code\res\drawable-xhdpi\afc_button_folders_view_grid.png
* third-party\android-filechooser\code\res\drawable-xhdpi\afc_button_folders_view_grid_disabled.png
* third-party\android-filechooser\code\res\drawable-xhdpi\afc_button_folders_view_grid_pressed.png
* third-party\android-filechooser\code\res\drawable-xhdpi\afc_button_folders_view_list.png
* third-party\android-filechooser\code\res\drawable-xhdpi\afc_button_folders_view_list_disabled.png
* third-party\android-filechooser\code\res\drawable-xhdpi\afc_button_folders_view_list_pressed.png
* third-party\android-filechooser\code\res\drawable-xhdpi\afc_button_navi_left.png
* third-party\android-filechooser\code\res\drawable-xhdpi\afc_button_navi_left_disabled.png
* third-party\android-filechooser\code\res\drawable-xhdpi\afc_button_navi_left_pressed.png
* third-party\android-filechooser\code\res\drawable-xhdpi\afc_button_navi_right.png
* third-party\android-filechooser\code\res\drawable-xhdpi\afc_button_navi_right_disabled.png
* third-party\android-filechooser\code\res\drawable-xhdpi\afc_button_navi_right_pressed.png
* third-party\android-filechooser\code\res\drawable-xhdpi\afc_button_ok_saveas.png
* third-party\android-filechooser\code\res\drawable-xhdpi\afc_button_ok_saveas_pressed.png
* third-party\android-filechooser\code\res\drawable-xhdpi\afc_context_menu_item_divider.png
* third-party\android-filechooser\code\res\drawable-xhdpi\afc_file.png
* third-party\android-filechooser\code\res\drawable-xhdpi\afc_file_audio.png
* third-party\android-filechooser\code\res\drawable-xhdpi\afc_file_compressed.png
* third-party\android-filechooser\code\res\drawable-xhdpi\afc_file_disabled.png
* third-party\android-filechooser\code\res\drawable-xhdpi\afc_file_image.png
* third-party\android-filechooser\code\res\drawable-xhdpi\afc_file_plain_text.png
* third-party\android-filechooser\code\res\drawable-xhdpi\afc_file_video.png
* third-party\android-filechooser\code\res\drawable-xhdpi\afc_folder.png
* third-party\android-filechooser\code\res\drawable-xhdpi\afc_folder_disabled.png
* third-party\android-filechooser\code\res\drawable-xhdpi\afc_folder_locked.png
* third-party\android-filechooser\code\res\drawable-xhdpi\afc_ic_menu_gridview.png
* third-party\android-filechooser\code\res\drawable-xhdpi\afc_ic_menu_home.png
* third-party\android-filechooser\code\res\drawable-xhdpi\afc_ic_menu_listview.png
* third-party\android-filechooser\code\res\drawable-xhdpi\afc_ic_menu_reload.png
* third-party\android-filechooser\code\res\drawable-xhdpi\afc_ic_menu_sort_by_date_asc.png
* third-party\android-filechooser\code\res\drawable-xhdpi\afc_ic_menu_sort_by_date_desc.png
* third-party\android-filechooser\code\res\drawable-xhdpi\afc_ic_menu_sort_by_name_asc.png
* third-party\android-filechooser\code\res\drawable-xhdpi\afc_ic_menu_sort_by_name_desc.png
* third-party\android-filechooser\code\res\drawable-xhdpi\afc_ic_menu_sort_by_size_asc.png
* third-party\android-filechooser\code\res\drawable-xhdpi\afc_ic_menu_sort_by_size_desc.png
* third-party\android-filechooser\code\res\drawable\
* third-party\android-filechooser\code\res\drawable\afc_button_location_dark_pressed.xml
* third-party\android-filechooser\code\res\drawable\afc_item_file.xml
* third-party\android-filechooser\code\res\drawable\afc_item_folder.xml
* third-party\android-filechooser\code\res\drawable\afc_selector_button_create_folder.xml
* third-party\android-filechooser\code\res\drawable\afc_selector_button_folders_view_grid.xml
* third-party\android-filechooser\code\res\drawable\afc_selector_button_folders_view_list.xml
* third-party\android-filechooser\code\res\drawable\afc_selector_button_location_dark.xml
* third-party\android-filechooser\code\res\drawable\afc_selector_button_navi_left.xml
* third-party\android-filechooser\code\res\drawable\afc_selector_button_navi_right.xml
* third-party\android-filechooser\code\res\drawable\afc_selector_button_ok_saveas.xml
* third-party\android-filechooser\code\res\drawable\afc_selector_button_sort_as.xml
* third-party\android-filechooser\code\res\drawable\afc_selector_button_sort_de.xml
* third-party\android-filechooser\code\res\layout-v11\
* third-party\android-filechooser\code\res\layout-v11\afc_file_chooser.xml
* third-party\android-filechooser\code\res\layout-v11\afc_settings_sort_view.xml
* third-party\android-filechooser\code\res\layout\
* third-party\android-filechooser\code\res\layout\afc_button_location.xml
* third-party\android-filechooser\code\res\layout\afc_context_menu_tiem.xml
* third-party\android-filechooser\code\res\layout\afc_context_menu_view.xml
* third-party\android-filechooser\code\res\layout\afc_file_chooser.xml
* third-party\android-filechooser\code\res\layout\afc_file_item.xml
* third-party\android-filechooser\code\res\layout\afc_gridview_files.xml
* third-party\android-filechooser\code\res\layout\afc_listview_files.xml
* third-party\android-filechooser\code\res\layout\afc_settings_sort_view.xml
* third-party\android-filechooser\code\res\layout\afc_simple_text_input_view.xml
* third-party\android-filechooser\code\res\menu\
* third-party\android-filechooser\code\res\menu\afc_file_chooser_activity.xml
* third-party\android-filechooser\code\res\values-es\
* third-party\android-filechooser\code\res\values-es\strings.xml
* third-party\android-filechooser\code\res\values-hdpi\
* third-party\android-filechooser\code\res\values-hdpi\dimens.xml
* third-party\android-filechooser\code\res\values-ldpi\
* third-party\android-filechooser\code\res\values-ldpi\dimens.xml
* third-party\android-filechooser\code\res\values-mdpi\
* third-party\android-filechooser\code\res\values-mdpi\dimens.xml
* third-party\android-filechooser\code\res\values-ru\
* third-party\android-filechooser\code\res\values-ru\strings.xml
* third-party\android-filechooser\code\res\values-v11\
* third-party\android-filechooser\code\res\values-v11\styles.xml
* third-party\android-filechooser\code\res\values-v14\
* third-party\android-filechooser\code\res\values-v14\styles.xml
* third-party\android-filechooser\code\res\values-vi\
* third-party\android-filechooser\code\res\values-vi\strings.xml
* third-party\android-filechooser\code\res\values-xhdpi\
* third-party\android-filechooser\code\res\values-xhdpi\dimens.xml
* third-party\android-filechooser\code\res\values\
* third-party\android-filechooser\code\res\values\colors.xml
* third-party\android-filechooser\code\res\values\dimens.xml
* third-party\android-filechooser\code\res\values\preferences.xml
* third-party\android-filechooser\code\res\values\strings.xml
* third-party\android-filechooser\code\res\values\styles.xml
* third-party\android-filechooser\code\src\
* third-party\android-filechooser\code\src\group\
* third-party\android-filechooser\code\src\group\pals\
* third-party\android-filechooser\code\src\group\pals\android\
* third-party\android-filechooser\code\src\group\pals\android\lib\
* third-party\android-filechooser\code\src\group\pals\android\lib\ui\
* third-party\android-filechooser\code\src\group\pals\android\lib\ui\filechooser\
* third-party\android-filechooser\code\src\group\pals\android\lib\ui\filechooser\FileChooserActivity.java
* third-party\android-filechooser\code\src\group\pals\android\lib\ui\filechooser\IFileAdapter.java
* third-party\android-filechooser\code\src\group\pals\android\lib\ui\filechooser\IFileDataModel.java
* third-party\android-filechooser\code\src\group\pals\android\lib\ui\filechooser\io\
* third-party\android-filechooser\code\src\group\pals\android\lib\ui\filechooser\io\IFile.java
* third-party\android-filechooser\code\src\group\pals\android\lib\ui\filechooser\io\IFileFilter.java
* third-party\android-filechooser\code\src\group\pals\android\lib\ui\filechooser\io\localfile\
* third-party\android-filechooser\code\src\group\pals\android\lib\ui\filechooser\io\localfile\LocalFile.java
* third-party\android-filechooser\code\src\group\pals\android\lib\ui\filechooser\io\localfile\ParentFile.java
* third-party\android-filechooser\code\src\group\pals\android\lib\ui\filechooser\prefs\
* third-party\android-filechooser\code\src\group\pals\android\lib\ui\filechooser\prefs\DisplayPrefs.java
* third-party\android-filechooser\code\src\group\pals\android\lib\ui\filechooser\prefs\Prefs.java
* third-party\android-filechooser\code\src\group\pals\android\lib\ui\filechooser\services\
* third-party\android-filechooser\code\src\group\pals\android\lib\ui\filechooser\services\FileProviderService.java
* third-party\android-filechooser\code\src\group\pals\android\lib\ui\filechooser\services\IFileProvider.java
* third-party\android-filechooser\code\src\group\pals\android\lib\ui\filechooser\services\LocalFileProvider.java
* third-party\android-filechooser\code\src\group\pals\android\lib\ui\filechooser\utils\
* third-party\android-filechooser\code\src\group\pals\android\lib\ui\filechooser\utils\ActivityCompat.java
* third-party\android-filechooser\code\src\group\pals\android\lib\ui\filechooser\utils\Converter.java
* third-party\android-filechooser\code\src\group\pals\android\lib\ui\filechooser\utils\DateUtils.java
* third-party\android-filechooser\code\src\group\pals\android\lib\ui\filechooser\utils\E.java
* third-party\android-filechooser\code\src\group\pals\android\lib\ui\filechooser\utils\FileComparator.java
* third-party\android-filechooser\code\src\group\pals\android\lib\ui\filechooser\utils\FileUtils.java
* third-party\android-filechooser\code\src\group\pals\android\lib\ui\filechooser\utils\MimeTypes.java
* third-party\android-filechooser\code\src\group\pals\android\lib\ui\filechooser\utils\TextUtils.java
* third-party\android-filechooser\code\src\group\pals\android\lib\ui\filechooser\utils\Ui.java
* third-party\android-filechooser\code\src\group\pals\android\lib\ui\filechooser\utils\Utils.java
* third-party\android-filechooser\code\src\group\pals\android\lib\ui\filechooser\utils\history\
* third-party\android-filechooser\code\src\group\pals\android\lib\ui\filechooser\utils\history\History.java
* third-party\android-filechooser\code\src\group\pals\android\lib\ui\filechooser\utils\history\HistoryFilter.java
* third-party\android-filechooser\code\src\group\pals\android\lib\ui\filechooser\utils\history\HistoryListener.java
* third-party\android-filechooser\code\src\group\pals\android\lib\ui\filechooser\utils\history\HistoryStore.java
* third-party\android-filechooser\code\src\group\pals\android\lib\ui\filechooser\utils\ui\
* third-party\android-filechooser\code\src\group\pals\android\lib\ui\filechooser\utils\ui\ContextMenuUtils.java
* third-party\android-filechooser\code\src\group\pals\android\lib\ui\filechooser\utils\ui\Dlg.java
* third-party\android-filechooser\code\src\group\pals\android\lib\ui\filechooser\utils\ui\LoadingDialog.java
* third-party\android-filechooser\code\src\group\pals\android\lib\ui\filechooser\utils\ui\MenuItemAdapter.java
* third-party\android-filechooser\code\src\group\pals\android\lib\ui\filechooser\utils\ui\TaskListener.java
* third-party\android-filechooser\code\src\group\pals\android\lib\ui\filechooser\utils\ui\ViewFilesContextMenuUtils.java
* third-party\drag-sort-listview\
* third-party\drag-sort-listview\CHANGELOG.md
* third-party\drag-sort-listview\README.md
* third-party\drag-sort-listview\library\
* third-party\drag-sort-listview\library\.gitignore
* third-party\drag-sort-listview\library\AndroidManifest.xml
* third-party\drag-sort-listview\library\ant.properties
* third-party\drag-sort-listview\library\build.xml
* third-party\drag-sort-listview\library\pom.xml
* third-party\drag-sort-listview\library\proguard-project.txt
* third-party\drag-sort-listview\library\project.properties
* third-party\drag-sort-listview\library\res\
* third-party\drag-sort-listview\library\res\values\
* third-party\drag-sort-listview\library\res\values\dslv_attrs.xml
* third-party\drag-sort-listview\library\src\
* third-party\drag-sort-listview\library\src\com\
* third-party\drag-sort-listview\library\src\com\mobeta\
* third-party\drag-sort-listview\library\src\com\mobeta\android\
* third-party\drag-sort-listview\library\src\com\mobeta\android\dslv\
* third-party\drag-sort-listview\library\src\com\mobeta\android\dslv\DragSortController.java
* third-party\drag-sort-listview\library\src\com\mobeta\android\dslv\DragSortCursorAdapter.java
* third-party\drag-sort-listview\library\src\com\mobeta\android\dslv\DragSortItemView.java
* third-party\drag-sort-listview\library\src\com\mobeta\android\dslv\DragSortItemViewCheckable.java
* third-party\drag-sort-listview\library\src\com\mobeta\android\dslv\DragSortListView.java
* third-party\drag-sort-listview\library\src\com\mobeta\android\dslv\ResourceDragSortCursorAdapter.java
* third-party\drag-sort-listview\library\src\com\mobeta\android\dslv\SimpleDragSortCursorAdapter.java
* third-party\drag-sort-listview\library\src\com\mobeta\android\dslv\SimpleFloatViewManager.java
* third-party\drag-sort-listview\pom.xml
* third-party\drag-sort-listview\tools\
* third-party\drag-sort-listview\tools\demo-sign-align.sh
* third-party\drag-sort-listview\tools\doc-dslv.sh
* third-party\drag-sort-listview\tools\dslv.py
* third-party\drag-sort-listview\tools\pkg-dslv.sh

From code:

* build.xml
* proguard-project.txt
* proguard.cfg

From library:

* .gitignore
* ant.properties
* build.xml
* pom.xml
* proguard-project.txt

Replaced Jars with Dependencies:
--------------------------------
The importer recognized the following .jar files as third party
libraries and replaced them with Gradle dependencies instead. This has
the advantage that more explicit version information is known, and the
libraries can be updated automatically. However, it is possible that
the .jar file in your project was of an older version than the
dependency we picked, which could render the project not compileable.
You can disable the jar replacement in the import wizard and try again:

android-support-v4.jar => com.android.support:support-v4:18.0.0

Moved Files:
------------
Android Gradle projects use a different directory structure than ADT
Eclipse projects. Here's how the projects were restructured:

In AmbilWarna:

* AndroidManifest.xml => ambilWarna\src\main\AndroidManifest.xml
* res\ => ambilWarna\src\main\res\
* src\ => ambilWarna\src\main\java\

In code:

* AndroidManifest.xml => code\src\main\AndroidManifest.xml
* res\ => code\src\main\res\
* src\ => code\src\main\java\

In library:

* AndroidManifest.xml => library\src\main\AndroidManifest.xml
* res\ => library\src\main\res\
* src\ => library\src\main\java\

In FBReaderJ:

* AndroidManifest.xml => fBReaderJ\src\main\AndroidManifest.xml
* assets\ => fBReaderJ\src\main\assets\
* jni\ => fBReaderJ\src\main\jni\
* libs\httpmime-4.2.5.jar => fBReaderJ\libs\httpmime-4.2.5.jar
* libs\json-simple-1.1.1.jar => fBReaderJ\libs\json-simple-1.1.1.jar
* libs\LingvoIntegration_2.5.2.12.jar => fBReaderJ\libs\LingvoIntegration_2.5.2.12.jar
* libs\nanohttpd-2.0.5.jar => fBReaderJ\libs\nanohttpd-2.0.5.jar
* libs\open-dictionary-api-1.2.1.jar => fBReaderJ\libs\open-dictionary-api-1.2.1.jar
* libs\pdfparse.jar => fBReaderJ\libs\pdfparse.jar
* res\ => fBReaderJ\src\main\res\
* src\ => fBReaderJ\src\main\java\
* src\org\geometerplus\android\fbreader\api\ApiInterface.aidl => fBReaderJ\src\main\aidl\org\geometerplus\android\fbreader\api\ApiInterface.aidl
* src\org\geometerplus\android\fbreader\api\ApiObject.aidl => fBReaderJ\src\main\aidl\org\geometerplus\android\fbreader\api\ApiObject.aidl
* src\org\geometerplus\android\fbreader\api\TextPosition.aidl => fBReaderJ\src\main\aidl\org\geometerplus\android\fbreader\api\TextPosition.aidl
* src\org\geometerplus\android\fbreader\config\ConfigInterface.aidl => fBReaderJ\src\main\aidl\org\geometerplus\android\fbreader\config\ConfigInterface.aidl
* src\org\geometerplus\android\fbreader\formatPlugin\CoverReader.aidl => fBReaderJ\src\main\aidl\org\geometerplus\android\fbreader\formatPlugin\CoverReader.aidl
* src\org\geometerplus\android\fbreader\httpd\DataInterface.aidl => fBReaderJ\src\main\aidl\org\geometerplus\android\fbreader\httpd\DataInterface.aidl
* src\org\geometerplus\android\fbreader\libraryService\LibraryInterface.aidl => fBReaderJ\src\main\aidl\org\geometerplus\android\fbreader\libraryService\LibraryInterface.aidl
* src\org\geometerplus\android\fbreader\libraryService\PositionWithTimestamp.aidl => fBReaderJ\src\main\aidl\org\geometerplus\android\fbreader\libraryService\PositionWithTimestamp.aidl
* src\org\geometerplus\android\fbreader\network\BookDownloaderInterface.aidl => fBReaderJ\src\main\aidl\org\geometerplus\android\fbreader\network\BookDownloaderInterface.aidl

Next Steps:
-----------
You can now build the project. The Gradle project needs network
connectivity to download dependencies.

Bugs:
-----
If for some reason your project does not build, and you determine that
it is due to a bug or limitation of the Eclipse to Gradle importer,
please file a bug at http://b.android.com with category
Component-Tools.

(This import summary is for your information only, and can be deleted
after import once you are satisfied with the results.)
