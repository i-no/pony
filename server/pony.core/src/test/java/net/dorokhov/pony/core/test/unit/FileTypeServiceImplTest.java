package net.dorokhov.pony.core.test.unit;

import net.dorokhov.pony.core.file.FileTypeService;
import net.dorokhov.pony.core.file.FileTypeServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FileTypeServiceImplTest {

	private FileTypeServiceImpl service;

	@Before
	public void setUp() {
		service = new FileTypeServiceImpl();
	}

	@Test
	public void test() {

		Assert.assertEquals("image/png", service.getFileMimeType("foobar.png"));
		Assert.assertEquals("image/jpeg", service.getFileMimeType("foobar.jpg"));
		Assert.assertEquals("image/jpeg", service.getFileMimeType("foobar.jpeg"));
		Assert.assertEquals("audio/mpeg", service.getFileMimeType("foobar.mp3"));
		Assert.assertEquals("audio/mpeg", service.getFileMimeType("foobar.MP3"));
		Assert.assertNull(service.getFileMimeType(".foobar.png"));
		Assert.assertNull(service.getFileMimeType("foobar.txt"));

		Assert.assertEquals("png", service.getFileExtension("image/png"));
		Assert.assertEquals("jpg", service.getFileExtension("image/jpeg"));
		Assert.assertEquals("mp3", service.getFileExtension("audio/mpeg"));
		Assert.assertNull(service.getFileExtension("text/plain"));

		Assert.assertEquals(FileTypeService.FileType.IMAGE, service.getFileType("foobar.jpg"));
		Assert.assertEquals(FileTypeService.FileType.IMAGE, service.getFileType("foobar.jpeg"));
		Assert.assertEquals(FileTypeService.FileType.IMAGE, service.getFileType("foobar.png"));
		Assert.assertEquals(FileTypeService.FileType.IMAGE, service.getFileType("foobar.PNG"));
		Assert.assertEquals(FileTypeService.FileType.SONG, service.getFileType("foobar.mp3"));
		Assert.assertNull(service.getFileType(".foobar.jpg"));
		Assert.assertNull(service.getFileType("foobar.txt"));
	}

}
