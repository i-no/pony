package net.dorokhov.pony.web.service;

import net.dorokhov.pony.core.domain.LogMessage;
import net.dorokhov.pony.core.logging.LogService;
import net.dorokhov.pony.web.domain.ListDto;
import net.dorokhov.pony.web.domain.LogMessageDto;
import net.dorokhov.pony.web.domain.LogQueryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class LogServiceFacadeImpl implements LogServiceFacade {

	private LogService logService;

	@Autowired
	public void setLogService(LogService aLogService) {
		logService = aLogService;
	}

	@Override
	public ListDto<LogMessageDto> getByQuery(LogQueryDto aQuery, int aPageNumber, int aPageSize) {

		LogMessage.Type type = aQuery.getType();
		if (type == null) {
			type = LogMessage.Type.DEBUG;
		}

		Date minDate = aQuery.getMinDate();
		if (minDate == null) {
			minDate = new Date(Long.MIN_VALUE);
		}

		Date maxDate = aQuery.getMaxDate();
		if (maxDate == null) {
			maxDate = new Date(Long.MAX_VALUE);
		}

		return ListDto.valueOf(logService.getByTypeAndDate(type, minDate, maxDate, new PageRequest(aPageNumber, aPageSize)), new ListDto.ContentConverter<LogMessage, LogMessageDto>() {
			@Override
			public LogMessageDto convert(LogMessage aItem) {
				return LogMessageDto.valueOf(aItem);
			}
		});
	}

}