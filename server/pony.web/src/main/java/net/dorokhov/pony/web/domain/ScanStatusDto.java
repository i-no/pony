package net.dorokhov.pony.web.domain;

import net.dorokhov.pony.core.domain.ScanType;
import net.dorokhov.pony.core.library.ScanService;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ScanStatusDto {

	private ScanType scanType;

	private List<String> files;

	private int step;

	private int totalSteps;

	private String stepCode;

	private double progress;

	public ScanType getScanType() {
		return scanType;
	}

	public void setScanType(ScanType aScanType) {
		scanType = aScanType;
	}

	public List<String> getFiles() {

		if (files == null) {
			files = new ArrayList<>();
		}

		return files;
	}

	public void setFiles(List<String> aFiles) {
		files = aFiles;
	}

	public int getStep() {
		return step;
	}

	public void setStep(int aStep) {
		step = aStep;
	}

	public int getTotalSteps() {
		return totalSteps;
	}

	public void setTotalSteps(int aTotalSteps) {
		totalSteps = aTotalSteps;
	}

	public String getStepCode() {
		return stepCode;
	}

	public void setStepCode(String aStepCode) {
		stepCode = aStepCode;
	}

	public double getProgress() {
		return progress;
	}

	public void setProgress(double aProgress) {
		progress = aProgress;
	}

	public static ScanStatusDto valueOf(ScanService.Status aScanStatus) {

		ScanStatusDto dto = new ScanStatusDto();

		dto.setScanType(aScanStatus.getScanType());
		dto.setStep(aScanStatus.getStep());
		dto.setTotalSteps(aScanStatus.getTotalSteps());
		dto.setStepCode(aScanStatus.getStepCode());
		dto.setProgress(aScanStatus.getProgress());

		for (File file : aScanStatus.getFiles()) {
			dto.getFiles().add(file.getAbsolutePath());
		}

		return dto;
	}
}
