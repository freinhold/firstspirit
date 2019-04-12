package com.reinhold.fs.hotspot;

import java.util.List;

import de.espirit.firstspirit.access.project.Group;
import de.espirit.firstspirit.access.store.templatestore.gom.GomIncludeValueProvider;
import de.espirit.firstspirit.agency.ProjectMembersAgent;
import de.espirit.firstspirit.agency.SpecialistsBroker;

public class ProjectGroupsHotspot implements GomIncludeValueProvider<Group> {

	@Override
	public String getKey(Group group) {
		return group.getName();
	}

	@Override
	public Class<Group> getType() {
		return Group.class;
	}

	@Override
	public List<Group> getValues(SpecialistsBroker broker) {
		return ((ProjectMembersAgent) broker
		        .requireSpecialist(ProjectMembersAgent.TYPE)).getGroups();
	}

}
