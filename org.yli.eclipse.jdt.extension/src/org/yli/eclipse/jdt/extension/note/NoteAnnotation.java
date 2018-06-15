package org.yli.eclipse.jdt.extension.note;

import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.source.Annotation;

import com.google.common.base.Strings;

public class NoteAnnotation extends Annotation {

	private static final String NOTE_ANNOTATION = "org.yli.eclipse.jdt.extension.noteSpecification";
	
	private final Position position;
	
	public NoteAnnotation(int offset, int length, String note) {
		super(NOTE_ANNOTATION, true, Strings.nullToEmpty(note));
		position = new Position(offset, length);
	}

	public Position getPosition() {
		return position;
	}

}
