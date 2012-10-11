/*
 * Copyright 2012 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Eclipse Public License version 1.0, available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jboss.forge.spec.javaee.jpa;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.jboss.forge.parser.java.Annotation;
import org.jboss.forge.parser.java.Field;
import org.jboss.forge.parser.java.JavaClass;
import org.jboss.forge.parser.java.JavaSource;
import org.jboss.forge.parser.java.util.Refactory;
import org.jboss.forge.parser.java.util.Types;
import org.jboss.forge.project.Project;
import org.jboss.forge.project.facets.JavaSourceFacet;
import org.jboss.forge.resources.Resource;
import org.jboss.forge.resources.java.JavaResource;
import org.jboss.forge.shell.PromptType;
import org.jboss.forge.shell.Shell;
import org.jboss.forge.shell.plugins.Alias;
import org.jboss.forge.shell.plugins.Command;
import org.jboss.forge.shell.plugins.DefaultCommand;
import org.jboss.forge.shell.plugins.Help;
import org.jboss.forge.shell.plugins.Option;
import org.jboss.forge.shell.plugins.Plugin;
import org.jboss.forge.shell.plugins.RequiresFacet;
import org.jboss.forge.shell.plugins.RequiresProject;
import org.jboss.forge.shell.plugins.RequiresResource;
import org.jboss.forge.spec.javaee.PersistenceFacet;

/**
 * @author <a href="mailto:lincolnbaxter@gmail.com">Lincoln Baxter, III</a>
 */
@Alias("field")
@RequiresProject
@RequiresFacet(PersistenceFacet.class)
@RequiresResource(JavaResource.class)
@Help("A plugin to manage simple @Entity and View creation; a basic MVC framework plugin.")
public class FieldPlugin implements Plugin
{
   private final Project project;
   private final Shell shell;

   @Inject
   public FieldPlugin(final Project project, final Shell shell)
   {
      this.project = project;
      this.shell = shell;
   }

   @DefaultCommand(help = "Add many custom field to an existing @Entity class")
   public void newExpressionField(
            @Option(required = true, description = "The field descriptor") final String... fields)
   {
      System.out.println(Arrays.asList(fields));
   }

   @Command(value = "custom", help = "Add a custom field to an existing @Entity class")
   public void newCustomField(
            @Option(name = "named",
                     required = true,
                     description = "The field name",
                     type = PromptType.JAVA_VARIABLE_NAME) final String fieldName,
            @Option(name = "type",
                     required = true,
                     type = PromptType.JAVA_CLASS,
                     description = "The qualified Class to be used as this field's type") final String type
            )
   {
      try
      {
         JavaClass entity = getJavaClass();
         String javaType = (type.toLowerCase().endsWith(".java")) ? type.substring(0, type.length() - 5) : type;

         addFieldTo(entity, javaType, fieldName, Column.class);
      }
      catch (FileNotFoundException e)
      {
         shell.println("Could not locate the @Entity requested. No update was made.");
      }
   }

   @Command(value = "boolean", help = "Add a boolean field to an existing @Entity class")
   public void newBooleanField(
            @Option(name = "named",
                     required = true,
                     description = "The field name",
                     type = PromptType.JAVA_VARIABLE_NAME) final String fieldName,
            @Option(name = "primitive",
                     required = false,
                     defaultValue = "true",
                     description = "Marks this field to be created as a primitive.",
                     type = PromptType.JAVA_VARIABLE_NAME) final boolean primitive)
   {
      try
      {
         JavaClass entity = getJavaClass();
         if (primitive)
         {
            addFieldTo(entity, boolean.class, fieldName, Column.class);
         }
         else
         {
            addFieldTo(entity, Boolean.class, fieldName, Column.class);
         }
      }
      catch (FileNotFoundException e)
      {
         shell.println("Could not locate the @Entity requested. No update was made.");
      }
   }

   @Command(value = "int", help = "Add an int field to an existing @Entity class")
   public void newIntField(
            @Option(name = "named",
                     required = true,
                     description = "The field name",
                     type = PromptType.JAVA_VARIABLE_NAME) final String fieldName,

            @Option(name = "primitive",
                     required = false,
                     defaultValue = "true",
                     description = "Marks this field to be created as a primitive.",
                     type = PromptType.JAVA_VARIABLE_NAME) final boolean primitive)
   {
      try
      {
         JavaClass entity = getJavaClass();
         if (primitive)
         {
            addFieldTo(entity, int.class, fieldName, Column.class);
         }
         else
         {
            addFieldTo(entity, Integer.class, fieldName, Column.class);
         }
      }
      catch (FileNotFoundException e)
      {
         shell.println("Could not locate the @Entity requested. No update was made.");
      }
   }

   @Command(value = "long", help = "Add a long field to an existing @Entity class")
   public void newLongField(
            @Option(name = "named",
                     required = true,
                     description = "The field name",
                     type = PromptType.JAVA_VARIABLE_NAME) final String fieldName,
            @Option(name = "primitive",
                     required = false,
                     defaultValue = "true",
                     description = "Marks this field to be created as a primitive.",
                     type = PromptType.JAVA_VARIABLE_NAME) final boolean primitive)
   {
      try
      {
         JavaClass entity = getJavaClass();
         if (primitive)
         {
            addFieldTo(entity, long.class, fieldName, Column.class);
         }
         else
         {
            addFieldTo(entity, Long.class, fieldName, Column.class);
         }
      }
      catch (FileNotFoundException e)
      {
         shell.println("Could not locate the @Entity requested. No update was made.");
      }
   }

   @Command(value = "number", help = "Add a number field to an existing @Entity class")
   public void newNumberField(
            @Option(name = "named",
                     required = true,
                     description = "The field name",
                     type = PromptType.JAVA_VARIABLE_NAME) final String fieldName,
            @Option(name = "type",
                     required = true,
                     type = PromptType.JAVA_CLASS,
                     description = "The qualified Class to be used as this field's type") final String type)
   {
      try
      {
         JavaClass entity = getJavaClass();
         addFieldTo(entity, Class.forName(type), fieldName, Column.class);
      }
      catch (FileNotFoundException e)
      {
         shell.println("Could not locate the @Entity requested. No update was made.");
      }
      catch (ClassNotFoundException e)
      {
         shell.println("Sorry, I don't think [" + type
                  + "] is a valid Java number type. Try something in the 'java.lang.* or java.math*' packages.");
      }
   }

   @Command(value = "temporal", help = "Add a temporal field (java.util.Date) to an existing @Entity class")
   public void newTemporalField(
            @Option(name = "type",
                     required = true) final TemporalType temporalType,
            @Option(name = "named",
                     required = true,
                     description = "The field name",
                     type = PromptType.JAVA_VARIABLE_NAME) final String fieldName)
   {
      try
      {
         JavaClass entity = getJavaClass();
         addTemporalFieldTo(entity, Date.class, fieldName, temporalType);
      }
      catch (FileNotFoundException e)
      {
         shell.println("Could not locate the @Entity requested. No update was made.");
      }
   }

   @Command(value = "string", help = "Add a String field to an existing @Entity class")
   public void newStringField(
            @Option(name = "named",
                     required = true,
                     description = "The field name",
                     type = PromptType.JAVA_VARIABLE_NAME) final String fieldName)
   {
      try
      {
         JavaClass entity = getJavaClass();
         addFieldTo(entity, String.class, fieldName, Column.class);
      }
      catch (FileNotFoundException e)
      {
         shell.println("Could not locate the @Entity requested. No update was made.");
      }
   }

   @Command(value = "oneToOne", help = "Add a One-to-one relationship field to an existing @Entity class")
   public void newOneToOneRelationship(
            @Option(name = "named",
                     required = true,
                     description = "The field name",
                     type = PromptType.JAVA_VARIABLE_NAME) final String fieldName,
            @Option(name = "fieldType",
                     required = true,
                     description = "The @Entity type to which this field is a relationship",
                     type = PromptType.JAVA_CLASS) final String fieldType,
            @Option(name = "inverseFieldName",
                     required = false,
                     description = "Create a bi-directional relationship, using this value as the name of the inverse field.",
                     type = PromptType.JAVA_VARIABLE_NAME) final String inverseFieldName)
   {
      JavaSourceFacet java = project.getFacet(JavaSourceFacet.class);

      try
      {
         JavaClass fieldEntityClass = findEntity(fieldType);
         JavaClass entityClass = getJavaClass();
         Field<JavaClass> localField = addFieldTo(entityClass, fieldEntityClass, fieldName, OneToOne.class);
         if ((inverseFieldName != null) && !inverseFieldName.isEmpty())
         {
            Field<JavaClass> inverseField = addFieldTo(fieldEntityClass, entityClass, inverseFieldName, OneToOne.class);
            addBidirectional(fieldEntityClass, entityClass, inverseFieldName, fieldName, OneToOne.class);
            inverseField.getAnnotation(OneToOne.class).setStringValue("mappedBy", localField.getName());
            java.saveJavaSource(fieldEntityClass);
         }
      }
      catch (FileNotFoundException e)
      {
         shell.println("Could not locate the @Entity requested. No update was made.");
      }
   }

	private void addBidirectional(final JavaClass targetEntity, final JavaClass fieldEntity,
            final String fieldName, final String inverseName,
            final Class<? extends java.lang.annotation.Annotation> annotation) {
		shell.print("Adding Bidirection Relationship\n");
		if(annotation.isAssignableFrom(OneToOne.class)){
			addBidirectionalOneToOne(targetEntity, fieldName, inverseName);			
		}
		
		if(annotation.isAssignableFrom(OneToMany.class)){
			addBidirectionalOneToMany(targetEntity, fieldEntity, fieldName,
					inverseName);			
		}
		if(annotation.isAssignableFrom(ManyToMany.class)){
			addBidirectionalManyToMany(targetEntity, fieldEntity, fieldName, inverseName);
		}
	
   	}

	private void addBidirectionalManyToMany(JavaClass targetEntity,
			JavaClass fieldEntity, String fieldName, String inverseName) {
		
		// Set Bidirectional
		String method = new String();
		if(fieldEntity.hasMethodSignature("setBidirectional", new String[]{fieldEntity.getName()})){
			method = fieldEntity.getMethod("setBidirectional", fieldEntity.getName()).getBody(); 
		}else{
			fieldEntity.addMethod().setName("setBidirectional").setPublic().setReturnTypeVoid();
		}
		method += "Set<" + targetEntity.getName() + "> setOf"  + fieldName + " = this" + ".get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1) +
				"();\nfor (" + targetEntity.getName() + " " + targetEntity.getName().toLowerCase() + " : " +
				"setOf"  + fieldName + "){\n\t" +
				targetEntity.getName().toLowerCase() + ".get" +
				inverseName.substring(0,1).toUpperCase() + inverseName.substring(1) + "().add(this);\n}";
		fieldEntity.getMethod("setBidirectional").setBody(method);		
	
		// Remove Bidirectional
		method = new String();
		if(fieldEntity.hasMethodSignature("removeBidirectional", new String[]{fieldEntity.getName()})){
			method = fieldEntity.getMethod("removeBidirectional", fieldEntity.getName()).getBody(); 
		}else{
			fieldEntity.addMethod().setName("removeBidirectional").setPublic().setReturnTypeVoid();
		}
		method += "Set<" + targetEntity.getName() + "> setOf"  + fieldName + " = this" + ".get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1) +
				"();\nfor (" + targetEntity.getName() + " " + targetEntity.getName().toLowerCase() + " : " +
				"setOf"  + fieldName + "){\n\t" +
				targetEntity.getName().toLowerCase() + ".get" +
				inverseName.substring(0,1).toUpperCase() + inverseName.substring(1) + "().remove(this);\n}";
		fieldEntity.getMethod("removeBidirectional").setBody(method);	
	}

	private void addBidirectionalOneToOne(final JavaClass targetEntity,
			final String fieldName, final String inverseName) {
		// Set Bidirectional
		String method = new String();
		if(targetEntity.hasMethodSignature("setBidirectional", new String[]{targetEntity.getName()})){
			method = targetEntity.getMethod("setBidirectional", targetEntity.getName()).getBody(); 
		}else{
			targetEntity.addMethod().setName("setBidirectional").setPublic().setReturnTypeVoid();
		}
		method += "if("  + targetEntity.getName().toLowerCase() + ".get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1) +
				  "() !=null){\n\t" + targetEntity.getName().toLowerCase() + ".get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1) + 
				  "().set" + inverseName.substring(0,1).toUpperCase() + inverseName.substring(1) + "(this);\n}";
		targetEntity.getMethod("setBidirectional").setBody(method);
		
		// Remove Bidirectional
		method = new String();
		if(targetEntity.hasMethodSignature("removeBidirectional", new String[]{targetEntity.getName()})){
				method = targetEntity.getMethod("removeBidirectional", targetEntity.getName()).getBody(); 
		}else{
			targetEntity.addMethod().setName("removeBidirectional").setPublic().setReturnTypeVoid();
		}
		method += "if("  + targetEntity.getName().toLowerCase() + ".get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1) +
				  "() !=null){\n\t" + targetEntity.getName().toLowerCase() + ".get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1) + 
				  "().set" + inverseName.substring(0,1).toUpperCase() + inverseName.substring(1) + "(null);\n}";
		targetEntity.getMethod("removeBidirectional").setBody(method);
	}

	private void addBidirectionalOneToMany(final JavaClass manyEntity,
			final JavaClass oneEntity, final String oneName,
			final String manyName) {
		// Set Bidirectional
		String method = new String();
		if(oneEntity.hasMethodSignature("setBidirectional", new String[]{manyEntity.getName()})){
			method = oneEntity.getMethod("setBidirectional", oneEntity.getName()).getBody(); 
		}else{
			oneEntity.addMethod().setName("setBidirectional").setPublic().setReturnTypeVoid();
		}
		method += "Set<" + manyEntity.getName() + "> setOf"  + manyEntity.getName() + " = this" + ".get" + oneName.substring(0, 1).toUpperCase() + oneName.substring(1) +
				"();\nfor (" + manyEntity.getName() + " " + manyEntity.getName().toLowerCase() + " : " +
				"setOf"  + manyEntity.getName() + "){\n\t" +
				manyEntity.getName().toLowerCase() + ".set" +
				manyName.substring(0,1).toUpperCase() + manyName.substring(1) + "(this);\n}";
		oneEntity.getMethod("setBidirectional").setBody(method);
		
		// Remove Bidirectional
		method = new String();
		if(oneEntity.hasMethodSignature("removeBidirectional", new String[]{manyEntity.getName()})){
			method = oneEntity.getMethod("removeBidirectional", oneEntity.getName()).getBody(); 
		}else{
			oneEntity.addMethod().setName("removeBidirectional").setPublic().setReturnTypeVoid();
		}
		method += "Set<" + manyEntity.getName() + "> setOf"  + manyEntity.getName() + " = this" + ".get" + oneName.substring(0, 1).toUpperCase() + oneName.substring(1) +
				"();\nfor (" + manyEntity.getName() + " " + manyEntity.getName().toLowerCase() + " : " +
				"setOf"  + manyEntity.getName() + "){\n\t" +
				manyEntity.getName().toLowerCase() + ".set" +
				manyName.substring(0,1).toUpperCase() + manyName.substring(1) + "(null);\n}";
		oneEntity.getMethod("removeBidirectional").setBody(method);
	}

@Command(value = "manyToMany", help = "Add a many-to-many relationship field (java.lang.Set<?>) to an existing @Entity class")
   public void newManyToManyRelationship(
            @Option(name = "named",
                     required = true,
                     description = "The field name",
                     type = PromptType.JAVA_VARIABLE_NAME) final String fieldName,
            @Option(name = "fieldType",
                     required = true,
                     description = "The @Entity type to which this field is a relationship",
                     type = PromptType.JAVA_CLASS) final String fieldType,
            @Option(name = "inverseFieldName",
                     required = false,
                     description = "Create an bi-directional relationship, using this value as the name of the inverse field.",
                     type = PromptType.JAVA_VARIABLE_NAME) final String inverseFieldName)
   {

      JavaSourceFacet java = project.getFacet(JavaSourceFacet.class);

      try
      {
         JavaClass entity = getJavaClass();
         JavaClass otherEntity = findEntity(fieldType);

         entity.addImport(Set.class);
         entity.addImport(HashSet.class);
         entity.addImport(otherEntity.getQualifiedName());
         Field<JavaClass> field = entity.addField("private Set<" + otherEntity.getName() + "> " + fieldName
                  + "= new HashSet<"
                  + otherEntity.getName() + ">();");
         Annotation<JavaClass> annotation = field.addAnnotation(ManyToMany.class);
         Refactory.createGetterAndSetter(entity, field);
         addBidirectional(otherEntity, entity, fieldName, inverseFieldName, ManyToMany.class);
         if ((inverseFieldName != null) && !inverseFieldName.isEmpty())
         {
            annotation.setStringValue("mappedBy", inverseFieldName);

            otherEntity.addImport(Set.class);
            otherEntity.addImport(HashSet.class);
            otherEntity.addImport(entity.getQualifiedName());
            Field<JavaClass> otherField = otherEntity.addField("private Set<" + entity.getName() + "> "
                     + inverseFieldName
                     + "= new HashSet<" + entity.getName() + ">();");
            otherField.addAnnotation(ManyToMany.class);
            Refactory.createGetterAndSetter(otherEntity, otherField);

            java.saveJavaSource(otherEntity);
         }
         java.saveJavaSource(entity);
      }
      catch (FileNotFoundException e)
      {
         shell.println("Could not locate the @Entity requested. No update was made.");
      }

   }

   @Command(value = "oneToMany", help = "Add a one-to-many relationship field (java.lang.Set<?>) to an existing @Entity class")
   public void newOneToManyRelationship(
            @Option(name = "named",
                     required = true,
                     description = "The field name",
                     type = PromptType.JAVA_VARIABLE_NAME) final String fieldName,
            @Option(name = "fieldType",
                     required = true,
                     description = "The @Entity representing the 'many' side of the relationship.",
                     type = PromptType.JAVA_CLASS) final String fieldType,
            @Option(name = "inverseFieldName",
                     required = false,
                     description = "Create an bi-directional relationship, using this value as the name of the inverse field.",
                     type = PromptType.JAVA_VARIABLE_NAME) final String inverseFieldName)
   {
      JavaSourceFacet java = project.getFacet(JavaSourceFacet.class);

      try
      {
         JavaClass one = getJavaClass();
         JavaClass many = findEntity(fieldType);

         one.addImport(Set.class);
         one.addImport(HashSet.class);
         one.addImport(many.getQualifiedName());
         Field<JavaClass> oneField = one.addField("private Set<" + many.getName() + "> " + fieldName + "= new HashSet<"
                  + many.getName() + ">();");
         Annotation<JavaClass> annotation = oneField.addAnnotation(OneToMany.class);
         Refactory.createGetterAndSetter(one, oneField);

         if ((inverseFieldName != null) && !inverseFieldName.isEmpty())
         {
            annotation.setStringValue("mappedBy", inverseFieldName);
            annotation.setLiteralValue("cascade", "CascadeType.ALL");
            annotation.getOrigin().addImport(CascadeType.class);
            annotation.setLiteralValue("orphanRemoval", "true");
            addBidirectional(many, one, fieldName, inverseFieldName, OneToMany.class);
            many.addImport(one);
            Field<JavaClass> manyField = many.addField("private " + one.getName() + " " + inverseFieldName + ";");
            manyField.addAnnotation(ManyToOne.class);
            Refactory.createGetterAndSetter(many, manyField);
            java.saveJavaSource(many);
         }
         java.saveJavaSource(one);
      }
      catch (FileNotFoundException e)
      {
         shell.println("Could not locate the @Entity requested. No update was made.");
      }
   }

   @Command(value = "manyToOne", help = "Add a many-to-one relationship field to an existing @Entity class")
   public void newManyToOneRelationship(
            @Option(name = "named",
                     required = true,
                     description = "The field name",
                     type = PromptType.JAVA_VARIABLE_NAME) final String fieldName,
            @Option(name = "fieldType",
                     required = true,
                     description = "The @Entity representing the 'one' side of the relationship.",
                     type = PromptType.JAVA_CLASS) final String fieldType,
            @Option(name = "inverseFieldName",
                     required = false,
                     description = "Create an bi-directional relationship, using this value as the name of the inverse field.",
                     type = PromptType.JAVA_VARIABLE_NAME) final String inverseFieldName)
   {
      JavaSourceFacet java = project.getFacet(JavaSourceFacet.class);

      try
      {
         JavaClass many = getJavaClass();
         JavaClass one = findEntity(fieldType);

         many.addImport(one);
         Field<JavaClass> manyField = many.addField("private " + one.getName() + " " + fieldName + ";");
         manyField.addAnnotation(ManyToOne.class);
         Refactory.createGetterAndSetter(many, manyField);

         if ((inverseFieldName != null) && !inverseFieldName.isEmpty())
         {
            one.addImport(Set.class);
            one.addImport(HashSet.class);
            one.addImport(many.getQualifiedName());
            Field<JavaClass> oneField = one.addField("private Set<" + many.getName() + "> " + inverseFieldName
                     + "= new HashSet<"
                     + many.getName() + ">();");
            Annotation<JavaClass> oneAnnotation = oneField.addAnnotation(OneToMany.class).setStringValue("mappedBy",
                     fieldName);
            oneAnnotation.setLiteralValue("cascade", "CascadeType.ALL");
            oneAnnotation.getOrigin().addImport(CascadeType.class);
            addBidirectional(many, one, inverseFieldName, fieldName, OneToMany.class);

            Refactory.createGetterAndSetter(one, oneField);
            java.saveJavaSource(one);
         }
         java.saveJavaSource(many);
      }
      catch (FileNotFoundException e)
      {
         shell.println("Could not locate the @Entity requested. No update was made.");
      }
   }

   /*
    * Helpers
    */
   private Field<JavaClass> addFieldTo(final JavaClass targetEntity, final JavaClass fieldEntity,
            final String fieldName,
            final Class<? extends java.lang.annotation.Annotation> annotation)
            throws FileNotFoundException
   {
      if (targetEntity.hasField(fieldName))
      {
         throw new IllegalStateException("Entity already has a field named [" + fieldName + "]");
      }

      JavaSourceFacet java = project.getFacet(JavaSourceFacet.class);
 
      Field<JavaClass> field = targetEntity.addField();
      field.setName(fieldName).setPrivate().setType(fieldEntity.getName()).addAnnotation(annotation);
      targetEntity.addImport(fieldEntity.getQualifiedName());
      Refactory.createGetterAndSetter(targetEntity, field);
      updateToString(targetEntity);

      java.saveJavaSource(targetEntity);
      shell.println("Added field to " + targetEntity.getQualifiedName() + ": " + field);

      return field;
   }

   private Field<JavaClass> addFieldTo(final JavaClass targetEntity, final String fieldType, final String fieldName,
            final Class<Column> annotation) throws FileNotFoundException
   {
      if (targetEntity.hasField(fieldName))
      {
         throw new IllegalStateException("Entity already has a field named [" + fieldName + "]");
      }

      JavaSourceFacet java = project.getFacet(JavaSourceFacet.class);

      Field<JavaClass> field = targetEntity.addField();
      field.setName(fieldName).setPrivate().setType(Types.toSimpleName(fieldType)).addAnnotation(annotation);
      targetEntity.addImport(fieldType);
      Refactory.createGetterAndSetter(targetEntity, field);

      updateToString(targetEntity);
      java.saveJavaSource(targetEntity);
      shell.println("Added field to " + targetEntity.getQualifiedName() + ": " + field);

      return field;
   }

   private Field<JavaClass> addTemporalFieldTo(final JavaClass targetEntity, final Class<?> fieldType,
            final String fieldName,
            final TemporalType temporalType)
            throws FileNotFoundException
   {
      if (targetEntity.hasField(fieldName))
      {
         throw new IllegalStateException("Entity already has a field named [" + fieldName + "]");
      }
      JavaSourceFacet java = project.getFacet(JavaSourceFacet.class);

      Field<JavaClass> field = targetEntity.addField();
      field.setName(fieldName).setPrivate().setType(fieldType).addAnnotation(Temporal.class).setEnumValue(temporalType);
      if (!fieldType.getName().startsWith("java.lang.") && !fieldType.isPrimitive())
      {
         targetEntity.addImport(fieldType);
      }
      Refactory.createGetterAndSetter(targetEntity, field);
      updateToString(targetEntity);
      java.saveJavaSource(targetEntity);
      shell.println("Added field to " + targetEntity.getQualifiedName() + ": " + field);

      return field;
   }

   private Field<JavaClass> addFieldTo(final JavaClass targetEntity, final Class<?> fieldType, final String fieldName,
            final Class<? extends java.lang.annotation.Annotation> annotation)
            throws FileNotFoundException
   {
      if (targetEntity.hasField(fieldName))
      {
         throw new IllegalStateException("Entity already has a field named [" + fieldName + "]");
      }
      JavaSourceFacet java = project.getFacet(JavaSourceFacet.class);

      Field<JavaClass> field = targetEntity.addField();
      field.setName(fieldName).setPrivate().setType(fieldType).addAnnotation(annotation);
      if (!fieldType.getName().startsWith("java.lang.") && !fieldType.isPrimitive())
      {
         targetEntity.addImport(fieldType);
      }
      Refactory.createGetterAndSetter(targetEntity, field);
      updateToString(targetEntity);
      java.saveJavaSource(targetEntity);
      shell.println("Added field to " + targetEntity.getQualifiedName() + ": " + field);

      return field;
   }

   public void updateToString(final JavaClass targetEntity)
   {
      if (targetEntity.hasMethodSignature("toString"))
      {
         targetEntity.removeMethod(targetEntity.getMethod("toString"));
      }
      List<Field<JavaClass>> fields = new ArrayList<Field<JavaClass>>();
      for (Field<JavaClass> f : targetEntity.getFields())
      {
         if (!"id".equals(f.getName()) && !"version".equals(f.getName())
                  && (f.getTypeInspector().isPrimitive() || Types.isJavaLang(f.getType())))
         {
            fields.add(f);
         }
      }
      if (!fields.isEmpty())
      {
         Refactory.createToStringFromFields(targetEntity, fields);
      }
   }

   private JavaClass getJavaClass() throws FileNotFoundException
   {
      Resource<?> resource = shell.getCurrentResource();
      if (resource instanceof JavaResource)
      {
         return getJavaClassFrom(resource);
      }
      else
      {
         throw new RuntimeException("Current resource is not a JavaResource!");
      }

   }

   private JavaClass getJavaClassFrom(final Resource<?> resource) throws FileNotFoundException
   {
      JavaSource<?> source = ((JavaResource) resource).getJavaSource();
      if (!source.isClass())
      {
         throw new IllegalStateException("Current resource is not a JavaClass!");
      }
      return (JavaClass) source;
   }

   private JavaClass findEntity(final String entity) throws FileNotFoundException
   {
      JavaClass result = null;

      PersistenceFacet scaffold = project.getFacet(PersistenceFacet.class);
      JavaSourceFacet java = project.getFacet(JavaSourceFacet.class);

      if (entity != null)
      {
         result = getJavaClassFrom(java.getJavaResource(entity));
         if (result == null)
         {
            result = getJavaClassFrom(java.getJavaResource(scaffold.getEntityPackage() + "." + entity));
         }
      }

      if (result == null)
      {
         result = promptForEntity();
      }

      if (result == null)
      {
         throw new FileNotFoundException("Could not locate JavaClass on which to operate.");
      }

      return result;
   }

   private JavaClass promptForEntity()
   {
      PersistenceFacet scaffold = project.getFacet(PersistenceFacet.class);
      List<JavaClass> entities = scaffold.getAllEntities();
      List<String> entityNames = new ArrayList<String>();
      for (JavaClass javaClass : entities)
      {
         String fullName = javaClass.getPackage();
         if (!fullName.isEmpty())
         {
            fullName += ".";
         }
         fullName += javaClass.getName();

         entityNames.add(fullName);
      }

      if (!entityNames.isEmpty())
      {
         int index = shell.promptChoice("Which entity would you like to modify?", entityNames);
         return entities.get(index);
      }
      return null;
   }
}
