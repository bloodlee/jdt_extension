package org.yli.eclipse.jdt.extension.note;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.texteditor.IAnnotationImageProvider;
import org.yli.eclipse.jdt.extension.Activator;

public class NoteAnnotationImageProvider implements IAnnotationImageProvider {

	private static final String ANNOTATION_IMG = "icons/note-12.png";
	
	@Override
	public Image getManagedImage(Annotation annotation) {
		return null;
	}

	@Override
	public String getImageDescriptorId(Annotation annotation) {
		if (annotation instanceof NoteAnnotation) {
			return ANNOTATION_IMG;
		}
		return null;
	}

	@Override
	public ImageDescriptor getImageDescriptor(String imageDescritporId) {
		return Activator.getImageDescriptor(imageDescritporId);
	}

}
