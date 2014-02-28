package com.inovex.zabbixmobile.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.content.Context;

import com.inovex.zabbixmobile.R;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * A simple demonstration object we are creating and persisting to the database.
 */
@DatabaseTable(tableName = "events")
public class Event implements Comparable<Event>, Sharable {

	public static final int VALUE_OK = 0;

	public static final String COLUMN_ID = "eventid";
	@DatabaseField(id = true, columnName = COLUMN_ID)
	long id;
	public static final String COLUMN_OBJECT_ID = "objectid";
	@DatabaseField(columnName = COLUMN_OBJECT_ID)
	long objectId;
	public static final String COLUMN_TRIGGER = "triggerid";
	@DatabaseField(columnName = COLUMN_TRIGGER, canBeNull = true, foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true)
	Trigger trigger;
	// Caution: This is the timestamp in milliseconds!
	public static final String COLUMN_CLOCK = "clock";
	@DatabaseField(columnName = COLUMN_CLOCK)
	long clock;
	public static final String COLUMN_VALUE = "value";
	@DatabaseField(columnName = COLUMN_VALUE)
	int value;
	public static final String COLUMN_ACK = "acknowledged";
	@DatabaseField(columnName = COLUMN_ACK)
	boolean acknowledged;

	// only local
	@DatabaseField
	String hostNames;

	public Event() {
	}

	public Event(long id, long objectId, long clock, int value,
			boolean acknowledged) {
		this.id = id;
		this.objectId = objectId;
		this.clock = clock;
		this.value = value;
		this.acknowledged = acknowledged;
	}

	public String getDetailedString() {
		StringBuilder sb = new StringBuilder();
		sb.append("id=").append(id);
		sb.append(", ").append("source=").append(objectId);
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(clock);
		DateFormat dateFormatter = SimpleDateFormat.getDateTimeInstance(
				SimpleDateFormat.DEFAULT, SimpleDateFormat.DEFAULT);
		sb.append(", ").append("date=")
				.append(dateFormatter.format(cal.getTime()));
		sb.append(", ").append("value=").append(value);
		sb.append(", ").append("acknowledged=").append(acknowledged);
		sb.append(", ").append("trigger={").append(trigger).append("}");
		return sb.toString();
	}

	public long getId() {
		return id;
	}

	public long getObjectId() {
		return objectId;
	}

	public Trigger getTrigger() {
		return trigger;
	}

	public void setTrigger(Trigger t) {
		trigger = t;
	}

	public String getHostNames() {
		return hostNames;
	}

	public void setHostNames(String hostNames) {
		this.hostNames = hostNames;
	}

	public long getClock() {
		return clock;
	}

	public int getValue() {
		return value;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setObjectId(long objectId) {
		this.objectId = objectId;
	}

	public void setClock(long clock) {
		this.clock = clock;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public void setAcknowledged(boolean acknowledged) {
		this.acknowledged = acknowledged;
	}

	public boolean isAcknowledged() {
		return acknowledged;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("id=").append(id);
		sb.append(", ").append("objectId=").append(objectId);
		sb.append(", ").append("clock=").append(clock);
		sb.append(", ").append("value=").append(value);
		sb.append(", ").append("acknowledged=").append(acknowledged);
		sb.append(", ").append("hostNames=").append(hostNames);
		if (trigger != null)
			sb.append(", ").append("trigger={").append(trigger.toString())
					.append("}");
		return sb.toString();
	}

	@Override
	public int compareTo(Event another) {
		if (id == another.getId())
			return 0;
		if (clock < another.getClock())
			return 1;
		if (clock > another.getClock())
			return -1;
		if (trigger == null)
			return 1;
		if (another.getTrigger() == null)
			return -1;
		if (trigger.getId() > another.getTrigger().getId())
			return 1;
		else
			return -1;
	}

	@Override
	public String getSharableString(Context context) {
		return "Event sharable - "
				+ context.getResources().getString(
						R.string.widget_more_problems);
	}
}
