package com.myapi.web.date.service;

import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

@Service
public class DateService {

	public DateTime now() {
		return DateTime.now();
	}

}
