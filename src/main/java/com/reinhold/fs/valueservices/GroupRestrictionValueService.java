package com.reinhold.fs.valueservices;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import de.espirit.firstspirit.access.User;
import de.espirit.firstspirit.access.project.Group;
import de.espirit.firstspirit.agency.ProjectMembersAgent;
import de.espirit.firstspirit.agency.SpecialistsBroker;
import de.espirit.firstspirit.agency.UserAgent;
import de.espirit.firstspirit.service.value.ValueService;

/**
 * Provide "group_names" as a parameter of the ValueService. It has to contain a
 * comma-separated list of groups. The valueservice returns true, if user is
 * member of one of those groups, otherwise it returns false. With this
 * value-service you can hide/show single gom-form elements for specific groups.
 */
public class GroupRestrictionValueService implements ValueService {

	public GroupRestrictionValueService() {
	}

	@Override
	public Object getValue(SpecialistsBroker broker, Map<String, ?> params) {

		String group_names = (String) params.get("group_names");
		List<String> usergroups = Arrays.asList(group_names.split(","));

		User user = broker.requireSpecialist(UserAgent.TYPE).getUser();
		ProjectMembersAgent agent = broker.requireSpecialist(ProjectMembersAgent.TYPE);
		Collection<Group> groups = agent.getUserGroups(user);
		for (Group group : groups) {
			if (usergroups.contains(group.getName())) {
				return true;
			}
		}

		return false;
	}

}
