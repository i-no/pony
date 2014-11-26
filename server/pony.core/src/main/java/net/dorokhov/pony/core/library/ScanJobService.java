package net.dorokhov.pony.core.library;

import net.dorokhov.pony.core.domain.ScanJob;
import net.dorokhov.pony.core.library.exception.LibraryNotDefinedException;

import java.util.List;

public interface ScanJobService {

	public void addDelegate(Delegate aDelegate);

	public void removeDelegate(Delegate aDelegate);

	public ScanJob getById(Long aId);

	public ScanJob startScanJob() throws LibraryNotDefinedException;
	public ScanJob startEditJob(List<ScanEditCommand> aCommands);

	public void markCurrentJobsInterrupted();
	public void startAutoScanJobIfNeeded();

	public static interface Delegate {

		public void onJobCreation(ScanJob aJob);

		public void onJobUpdate(ScanJob aJob);
	}
}