package net.dorokhov.pony.core.service.audio;

import java.io.File;

public class SongDataWriteCommand {

	private File file;

	private Integer discNumber;
	private Integer discCount;

	private Integer trackNumber;
	private Integer trackCount;

	private String title;
	private String artist;
	private String albumArtist;
	private String album;

	private Integer year;

	private String genre;

	private File artwork;

	public SongDataWriteCommand(File aFile) {

		if (aFile == null) {
			throw new NullPointerException("File must not be null.");
		}

		file = aFile;
	}

	public File getFile() {
		return file;
	}

	public Integer getDiscNumber() {
		return discNumber;
	}

	public void setDiscNumber(Integer aDiscNumber) {
		discNumber = aDiscNumber;
	}

	public Integer getDiscCount() {
		return discCount;
	}

	public void setDiscCount(Integer aDiscCount) {
		discCount = aDiscCount;
	}

	public Integer getTrackNumber() {
		return trackNumber;
	}

	public void setTrackNumber(Integer aTrackNumber) {
		trackNumber = aTrackNumber;
	}

	public Integer getTrackCount() {
		return trackCount;
	}

	public void setTrackCount(Integer aTrackCount) {
		trackCount = aTrackCount;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String aTitle) {
		title = aTitle;
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String aArtist) {
		artist = aArtist;
	}

	public String getAlbumArtist() {
		return albumArtist;
	}

	public void setAlbumArtist(String aAlbumArtist) {
		albumArtist = aAlbumArtist;
	}

	public String getAlbum() {
		return album;
	}

	public void setAlbum(String aAlbum) {
		album = aAlbum;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer aYear) {
		year = aYear;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String aGenre) {
		genre = aGenre;
	}

	public File getArtwork() {
		return artwork;
	}

	public void setArtwork(File aArtwork) {
		artwork = aArtwork;
	}
}
