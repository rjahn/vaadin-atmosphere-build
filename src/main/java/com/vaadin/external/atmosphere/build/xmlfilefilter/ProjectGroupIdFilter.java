package com.vaadin.external.atmosphere.build.xmlfilefilter;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.File;

/**
 * Updates project groupId from org.atmosphere to
 * com.vaadin.external.atmosphered
 */
public class ProjectGroupIdFilter extends PomXmlFilter {

    public static final String GROUP_ID_PREFIX = "at.ganzleicht.vaadin.atmosphere";

    public ProjectGroupIdFilter(File root) {
        super(root);
    }

    @Override
    public void process(File f, Document d) throws Exception {
        updateGroupId((Element) findNode(d, "/project/groupId"));
        updateGroupId((Element) findNode(d, "/project/parent/groupId"));

        updateFile(f, d);
    }

    private void updateGroupId(Element e) {
        if (e == null) {
            return;
        }

        String groupId = e.getTextContent();
        if (groupId.startsWith("org.atmosphere")) {
            e.setTextContent(
                groupId.replace("org.atmosphere", GROUP_ID_PREFIX));
        } else if ("org.sonatype.oss".equals(groupId)) {
        } else if ("gwtexporter".equals(groupId)) {
            e.setTextContent(GROUP_ID_PREFIX + ".gwtexporter");
        } else {
            throw new IllegalStateException("Unexpected groupId: " + groupId);
        }
    }

}
