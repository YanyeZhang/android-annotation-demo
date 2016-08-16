package com.example;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;

//@SupportedSourceVersion(SourceVersion.RELEASE_6)
//@SupportedAnnotationTypes("com.zhangyanye.com.zhangyanye.annotationinject.annotation.apt.BindView")
@AutoService(Processor.class)
public class BindViewProcessor extends AbstractProcessor {

    private Filer filer;
    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        // Filer�Ǹ��ӿڣ�֧��ͨ��ע�⴦�����������ļ�
        filer = processingEnv.getFiler();
    }
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (Element element : roundEnv.getElementsAnnotatedWith(BindView.class)) {
            System.out.println("------------------------------");
            MethodSpec main = MethodSpec.methodBuilder("findViewById")
                    .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                    .returns(void.class)
                    .addStatement("$T.out.println($S)", System.class, "Hello, JavaPoet!")
                    .build();
            // �ж�Ԫ�ص�����ΪClass
            if (element.getKind() == ElementKind.CLASS) {
                // ��ʾת��Ԫ������
                VariableElement typeElement = (VariableElement) element;
                // ���Ԫ������
                System.out.println(typeElement.getSimpleName());
                // ���ע������ֵ
                System.out.println(typeElement.getAnnotation(BindView.class).id());
            }
            System.out.println("------------------------------");
        }

        for (TypeElement element : annotations) {
            if (element.getQualifiedName().toString().equals(BindView.class.getCanonicalName())) {
                // ����main����
                MethodSpec main = MethodSpec.methodBuilder("main")
                        .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                        .returns(void.class)
                        .addParameter(String[].class, "args")
                        .addStatement("$T.out.println($S)", System.class, "Hello, JavaPoet!")
                        .build();
                // ����HelloWorld��
                TypeSpec helloWorld = TypeSpec.classBuilder("HelloWorld")
                        .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                        .addMethod(main)
                        .build();

                try {
                    // ���� com.example.HelloWorld.java
                    JavaFile javaFile = JavaFile.builder("com.example", helloWorld)
                            .addFileComment(" This codes are generated automatically. Do not modify!")
                            .build();
                    //�������ļ�
                    javaFile.writeTo(filer);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotataions = new LinkedHashSet<>();
        annotataions.add(BindView.class.getCanonicalName());
        return annotataions;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }
}
