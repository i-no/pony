package net.dorokhov.pony.core.test.unit;

import net.dorokhov.pony.core.common.LibraryFolder;
import net.dorokhov.pony.core.common.LibraryImage;
import net.dorokhov.pony.core.service.ArtworkDiscoveryServiceImpl;
import net.dorokhov.pony.core.service.FileScannerImpl;
import net.dorokhov.pony.core.service.FileTypeServiceImpl;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ArtworkDiscoveryServiceImplTest {

	private static final String TEST_FILE_PATH = "data/image.png";
	private static final File TEST_FOLDER = new File(FileUtils.getTempDirectory(), "ArtworkServiceImplTest");

	private FileScannerImpl fileScanner;

	private ArtworkDiscoveryServiceImpl artworkDiscoveryService;

	@Before
	public void setUp() throws Exception {

		fileScanner = new FileScannerImpl();
		fileScanner.setFileTypeService(new FileTypeServiceImpl());

		artworkDiscoveryService = new ArtworkDiscoveryServiceImpl();

		artworkDiscoveryService.setArtworkMinSizeRatio(0.9);
		artworkDiscoveryService.setArtworkMaxSizeRatio(1.1);

		artworkDiscoveryService.setArtworkFileNames(new HashSet<>(Arrays.asList("cover")));
		artworkDiscoveryService.setArtworkFolderNames(new HashSet<>(Arrays.asList("artwork")));

		FileUtils.deleteDirectory(TEST_FOLDER);
	}

	@After
	public void tearDown() throws Exception {
		FileUtils.deleteDirectory(TEST_FOLDER);
	}

	@Test
	public void testDiscoveryOfNotExistingArtwork() throws Exception {

		TEST_FOLDER.mkdir();

		FileUtils.touch(new File(TEST_FOLDER, "song.mp3"));

		LibraryFolder testFolder = fileScanner.scanFolder(TEST_FOLDER);

		LibraryImage artwork = artworkDiscoveryService.discoverArtwork(testFolder.getChildSongByName("song.mp3"));

		Assert.assertNull(artwork);
	}

	@Test
	public void testDiscoveryInCurrentFolder() throws Exception {

		File testImage = new ClassPathResource(TEST_FILE_PATH).getFile();

		TEST_FOLDER.mkdir();

		FileUtils.touch(new File(TEST_FOLDER, "song.mp3"));

		File image = new File(TEST_FOLDER, "cover.png");

		FileUtils.copyFile(testImage, image);

		LibraryFolder testFolder = fileScanner.scanFolder(TEST_FOLDER);

		LibraryImage artwork = artworkDiscoveryService.discoverArtwork(testFolder.getChildSongByName("song.mp3"));

		Assert.assertEquals("cover.png", artwork.getFile().getName());
	}

	@Test
	public void testDiscoveryInParentFolder() throws Exception {

		File testImage = new ClassPathResource(TEST_FILE_PATH).getFile();

		TEST_FOLDER.mkdir();

		File childFolder = new File(TEST_FOLDER, "test");

		childFolder.mkdir();

		File image = new File(TEST_FOLDER, "cover.jpg");

		FileUtils.copyFile(testImage, image);

		FileUtils.touch(new File(childFolder, "song.mp3"));

		LibraryFolder testFolder = fileScanner.scanFolder(TEST_FOLDER);

		LibraryImage artwork = artworkDiscoveryService.discoverArtwork(testFolder.getChildFolderByName("test").getChildSongByName("song.mp3"));

		Assert.assertEquals("cover.jpg", artwork.getFile().getName());
	}

	@Test
	public void testDiscoveryInChildFolder() throws Exception {

		File testImage = new ClassPathResource(TEST_FILE_PATH).getFile();

		TEST_FOLDER.mkdir();

		FileUtils.touch(new File(TEST_FOLDER, "song.mp3"));

		File artworkFolder = new File(TEST_FOLDER, "artwork");

		artworkFolder.mkdir();

		File image = new File(artworkFolder, "image.jpg");

		FileUtils.copyFile(testImage, image);

		LibraryFolder testFolder = fileScanner.scanFolder(TEST_FOLDER);

		LibraryImage artwork = artworkDiscoveryService.discoverArtwork(testFolder.getChildSongByName("song.mp3"));

		Assert.assertEquals("image.jpg", artwork.getFile().getName());
	}

	@Test
	public void testFailedDiscovery() throws Exception {

		TEST_FOLDER.mkdir();

		FileUtils.touch(new File(TEST_FOLDER, "song.mp3"));
		FileUtils.touch(new File(TEST_FOLDER, "cover.jpg"));

		LibraryFolder testFolder = fileScanner.scanFolder(TEST_FOLDER);

		LibraryImage artwork = artworkDiscoveryService.discoverArtwork(testFolder.getChildSongByName("song.mp3"));

		Assert.assertNull(artwork);
	}

	@Test
	public void testConfiguration() {

		Set<String> result;

		artworkDiscoveryService.setArtworkFileNames(",cover, folder,artwork");

		result = artworkDiscoveryService.getArtworkFileNames();

		Assert.assertEquals(3, result.size());
		Assert.assertTrue(result.contains("cover"));
		Assert.assertTrue(result.contains("folder"));
		Assert.assertTrue(result.contains("artwork"));

		artworkDiscoveryService.setArtworkFolderNames("artwork, covers");

		result = artworkDiscoveryService.getArtworkFolderNames();

		Assert.assertEquals(2, result.size());
		Assert.assertTrue(result.contains("artwork"));
		Assert.assertTrue(result.contains("covers"));
	}
}