package org.zalando.intellij.swagger.validator.field;

import com.intellij.lang.annotation.Annotation;
import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.psi.PsiElement;
import org.zalando.intellij.swagger.completion.field.model.Field;
import org.zalando.intellij.swagger.intention.RemoveFieldIntentionAction;

import java.util.List;

class UnknownKeyValidator {

    private static final String VENDOR_EXTENSION_PREFIX = "x-";

    void validate(final String key,
                         final List<Field> availableKeys,
                         final PsiElement psiElement,
                         final AnnotationHolder annotationHolder) {
        boolean keyFoundInAvailableKeys = availableKeys.stream().anyMatch(field -> field.getName().equals(key));
        if (!keyFoundInAvailableKeys && !key.startsWith(VENDOR_EXTENSION_PREFIX)) {
            final Annotation errorAnnotation = annotationHolder.createErrorAnnotation(psiElement, "Invalid key");
            errorAnnotation.registerFix(new RemoveFieldIntentionAction(psiElement));
        }
    }
}