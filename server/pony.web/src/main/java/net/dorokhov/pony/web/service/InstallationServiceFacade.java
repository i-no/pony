package net.dorokhov.pony.web.service;

import net.dorokhov.pony.web.domain.InstallationCommandDto;
import net.dorokhov.pony.core.installation.exception.AlreadyInstalledException;
import net.dorokhov.pony.core.installation.exception.NotInstalledException;
import net.dorokhov.pony.web.domain.InstallationDto;

public interface InstallationServiceFacade {

	public InstallationDto getInstallation();

	public InstallationDto install(InstallationCommandDto aCommand) throws AlreadyInstalledException;

	public void uninstall() throws NotInstalledException;

}
