
grammar JavaPlain;
options {backtrack=true; memoize=true;}
@header{
package javaplain;
import java.util.HashSet;
}
@members{

public class Variable implements Comparable<Variable> {
	String dataType;
	String accessS;
	String vName;
	
	Variable(String dataType, String accessS, String vName){
		this.dataType=dataType;
		this.accessS=accessS;
		this.vName=vName;
	}
	
	public String getDataType() {return dataType;}
	public String getAccessS() {return accessS;}
	public String getVName() {return vName;}
	public int compareTo(Variable other) {
	
		if(!(this.vName.equals(other.vName))) return 1;
		if(!(this.accessS.equals(other.accessS))) return 2;
		if(!(this.dataType.equals(other.dataType))) return 3;
		
		return 0;
	}
	public boolean equals(Object o) {
		Variable other = (Variable) o;
		if(o==null) return false;
		if(!(this.vName.equals(other.vName))) return false;
		if(!(this.accessS.equals(other.accessS))) return false;
		if(!(this.dataType.equals(other.dataType))) return false;
		
		return true;
	}
	
	public String toString() {
		StringBuilder sb4 = new StringBuilder();
		
		sb4.append(this.vName);
		sb4.append(" : ");
		sb4.append(this.dataType);
		sb4.append(" : ");		
		sb4.append(this.accessS);
		sb4.append(" : ");		
		
		return sb4.toString();
	}
}

public class Method implements Comparable<Method> {
	String returnType;
	String accessS;
	String vName;
	
	Method(String returnType, String accessS, String vName){
		this.returnType=returnType;
		this.accessS=accessS;
		this.vName=vName;
	}
	
	public String getReturnType() {return returnType;}
	public String getAccessS() {return accessS;}
	public String getVName() {return vName;}
	public int compareTo(Method other) {
	
		if(!(this.vName.equals(other.vName))) return 1;
		if(!(this.accessS.equals(other.accessS))) return 2;
		if(!(this.returnType.equals(other.returnType))) return 3;
		
		return 0;
	}
	public boolean equals(Object o) {
		Method other = (Method) o;
		if(o==null) return false;
		if(!(this.vName.equals(other.vName))) return false;
		if(!(this.accessS.equals(other.accessS))) return false;
		if(!(this.returnType.equals(other.returnType))) return false;
		
		return true;
	}
	
	public String toString() {
		StringBuilder sb4 = new StringBuilder();
		
		sb4.append(this.vName);
		sb4.append(" : ");
		sb4.append(this.returnType);
		sb4.append(" : ");		
		sb4.append(this.accessS);
		sb4.append(" : ");		
		
		return sb4.toString();
	}
}
boolean isExtends=false, isImp=false;
int intCount=0;
public ArrayList<String> myBuffer = new ArrayList<>();
public String temp = "";

//packageName
public String packageName ="";

//ancestor descendent
public String className = "";
public Map<String,String> ancestor = new HashMap<>();
public Map<String, HashSet> descendent = new HashMap<>(); //main.descendentC.descendent.

public List classes = new ArrayList();
Map<String, ArrayList<String>> map = new HashMap<>();
String accessModifierClass="";
String isStatic="";

// Constructor Specific
boolean isConstructor=false;

//Method
String returnType="";
ArrayList<String> parameterType = new ArrayList<>();
ArrayList<String> parameterNames = new ArrayList<>();
String accessModifierMethod="";
boolean isMethod = false;
boolean isParameter = false;
public Map<String, ArrayList<String>> hm_class_methods = new HashMap<>();
public Map<String, List<Method>> hm_class_methods_2 = new HashMap<>();

//aggregateds
boolean isAggregate = false;
public HashMap<String,HashSet> hm_class_aggregates = new HashMap<>();

//associates
boolean isAssociate = false;
boolean isMain = true;
public HashMap<String,HashSet> hm_class_associates = new HashMap<>();

//data member i.e variables
String accessModifierDataMember="";
boolean isDataMember = false;
String returnTypeDataMember ="";
//public ArrayList<String> dataMembers = new ArrayList<>();
//public ArrayList<ArrayList<String>> indi_dataMembers = new ArrayList<>();
public HashMap<String,List<String>> hm_class_variables = new HashMap<String,List<String>>();
public HashMap<String,List<Variable>> hm_class_variables_2 = new HashMap<>();
public HashMap<String,List<String>> hm_class_localvariable = new HashMap<String,List<String>>();

boolean isLocalVariable = false;
}
@lexer::header{
package javaplain;
}
@lexer::members {
  protected boolean enumIsKeyword = true;
  protected boolean assertIsKeyword = true;
}

// starting point for parsing a java file
/* The annotations are separated out to make parsing faster, but must be associated with
   a packageDeclaration or a typeDeclaration (and not an empty one). */
compilationUnit
    :   annotations
        (   packageDeclaration importDeclaration* typeDeclaration*
        |   classOrInterfaceDeclaration typeDeclaration*
        )
    |   packageDeclaration? importDeclaration* typeDeclaration*
    ;

packageDeclaration
    :   'package' qualifiedName {packageName = $qualifiedName.text;}';'
    
    ;
    
importDeclaration
    :   'import' 'static'? qualifiedName ('.' '*')? ';'
    ;
    
typeDeclaration
    :   classOrInterfaceDeclaration
    |   ';'
    ;
    
classOrInterfaceDeclaration
    :   classOrInterfaceModifiers (classDeclaration | interfaceDeclaration)
    ;
    
classOrInterfaceModifiers
    :   classOrInterfaceModifier*
    ;

classOrInterfaceModifier
    :   annotation   // class or interface
    |   'public'     {accessModifierClass = "public";
    }// class or interface
    |   'protected'   {accessModifierClass = "protected";}// class or interface
    |   'private'     {accessModifierClass = "private";}// class or interface
    |   'abstract'   {accessModifierClass = "abstract";}// class or interface
    |   'static'     {accessModifierClass = "static";}// class or interface
    |   'final'      {accessModifierClass = "final";}// class only -- does not apply to interfaces
    |   'strictfp'   {accessModifierClass = "strictfp";}// class or interface
    ;

modifiers
    :   modifier*
    ;

classDeclaration
    :   normalClassDeclaration
    |   enumDeclaration
    ;
    
normalClassDeclaration
    :   'class' Identifier {
    		
    		className = $Identifier.text;
    		//System.out.println("Class:"+$Identifier.text); 
    		myBuffer.add("Class : " + $Identifier.text);
    		myBuffer.add("dm");
    		classes.add($Identifier.text);
    		//indi_dataMembers.add(dataMembers);
    		//System.out.println(dataMembers);
    		
    	} typeParameters?
        ('extends'{isExtends=true;} type)?
        ('implements'{isExtends=false;isImp= true;} typeList)?
        classBody
    ;
    
typeParameters
    :   '<' typeParameter (',' typeParameter)* '>'
    ;

typeParameter
    :   Identifier ('extends' typeBound)?
    ;
        
typeBound
    :   type ('&' type)*
    ;

enumDeclaration
    :   ENUM Identifier ('implements' typeList)? enumBody
    ;

enumBody
    :   '{' enumConstants? ','? enumBodyDeclarations? '}'
    ;

enumConstants
    :   enumConstant (',' enumConstant)*
    ;
    
enumConstant
    :   annotations? Identifier arguments? classBody?
    ;
    
enumBodyDeclarations
    :   ';' (classBodyDeclaration)*
    ;
    
interfaceDeclaration
    :   normalInterfaceDeclaration
    |   annotationTypeDeclaration
    ;
    
normalInterfaceDeclaration
    :   'interface' Identifier typeParameters? ('extends' typeList)? interfaceBody
    ;
    
typeList
    :   type (',' type)*
    ;
    
classBody
    :   '{' classBodyDeclaration* '}'
    ;
    
interfaceBody
    :   '{' interfaceBodyDeclaration* '}'
    ;

classBodyDeclaration
    :   ';'
    |   'static'? block
    |   modifiers memberDecl
    ;
    
memberDecl
    :   genericMethodOrConstructorDecl
    |   memberDeclaration
    |   'void' Identifier voidMethodDeclaratorRest
    |   Identifier constructorDeclaratorRest{
    	//System.out.print(accessModifierMethod + " " +$Identifier.text + "(" );
    	temp += accessModifierMethod + " " + $Identifier.text + "(";
    	for(int i = 0; i<parameterType.size();i++){
		//System.out.print(parameterType.get(i) + " " + parameterNames.get(i));
		temp += parameterType.get(i) + " " + parameterNames.get(i);
		if(i != parameterType.size()-1){
			//System.out.print(",");
			temp += ","; 
		}
	}
	//System.out.println(")");
	temp += ")";
	myBuffer.add(temp);
	//resetting the variables
	temp = "";
	accessModifierMethod="";
	returnType="";
	parameterType.clear();
	parameterNames.clear();
	isMethod=false;
	isParameter=false;
    }
    |   interfaceDeclaration
    |   classDeclaration
    ;
    
memberDeclaration
    :   {isMethod=true; isDataMember = true; isAggregate=true; isLocalVariable=false; isAssociate=true;}type (methodDeclaration | fieldDeclaration )
    ;

genericMethodOrConstructorDecl
    :   typeParameters genericMethodOrConstructorRest
    ;
    
genericMethodOrConstructorRest
    :   (type | 'void') Identifier methodDeclaratorRest
    |   Identifier constructorDeclaratorRest
    ;

methodDeclaration
    :   Identifier methodDeclaratorRest{
    	//System.out.print(accessModifierMethod + " " + returnType + " " +$Identifier.text + "(" );
    	temp += accessModifierMethod + " " + returnType + " " +$Identifier.text + "(" ;
    	   
	for(int i = 0; i<parameterType.size();i++){
		//System.out.print(parameterType.get(i) + " " + parameterNames.get(i));
		temp += parameterType.get(i) + " " + parameterNames.get(i);
		if(i != parameterType.size()-1){
			
			temp += ",";
		}
	}
	//System.out.println(")");
	temp += ")";
	myBuffer.add(temp);
	System.out.println("Inside method testing" +  accessModifierMethod + " " + returnType + " " +$Identifier.text + "(" );
	if(!hm_class_methods.containsKey(className)){
		hm_class_methods.put(className,new ArrayList());
		hm_class_methods_2.put(className, new ArrayList<>());
	}
	hm_class_methods_2.get(className).add(new Method(returnType, accessModifierMethod, $Identifier.text));
	hm_class_methods.get(className).add(temp);
	//resetting the variables
	temp = "";
	accessModifierMethod="";
	returnType="";
	parameterType.clear();
	parameterNames.clear();
	isMethod=false;
	isParameter=false;
    }
    ;

 fieldDeclaration
    :   {isDataMember = true;}variableDeclarators';'
    ;
             
interfaceBodyDeclaration
    :   modifiers interfaceMemberDecl
    |   ';'
    ;

interfaceMemberDecl
    :   interfaceMethodOrFieldDecl
    |   interfaceGenericMethodDecl
    |   'void' Identifier voidInterfaceMethodDeclaratorRest
    |   interfaceDeclaration
    |   classDeclaration
    ;
    
interfaceMethodOrFieldDecl
    :   type Identifier interfaceMethodOrFieldRest
    ;
    
interfaceMethodOrFieldRest
    :   constantDeclaratorsRest ';'
    |   interfaceMethodDeclaratorRest
    ;
    
methodDeclaratorRest
    :   formalParameters ('[' ']')*
        ('throws' qualifiedNameList)?
        (   methodBody
        |   ';'
        )
    ;
    
voidMethodDeclaratorRest
    :   formalParameters ('throws' qualifiedNameList)?
        (   methodBody
        |   ';'
        )
    ;
    
interfaceMethodDeclaratorRest
    :   formalParameters ('[' ']')* ('throws' qualifiedNameList)? ';'
    ;
    
interfaceGenericMethodDecl
    :   typeParameters (type | 'void') Identifier
        interfaceMethodDeclaratorRest
    ;
    
voidInterfaceMethodDeclaratorRest
    :   formalParameters ('throws' qualifiedNameList)? ';'
    ;
    
constructorDeclaratorRest
    :   formalParameters ('throws' qualifiedNameList)? constructorBody
    ;

constantDeclarator
    :   Identifier constantDeclaratorRest
    ;
    
variableDeclarators
    :   variableDeclarator (',' variableDeclarator)*
    ;

variableDeclarator
    :   variableDeclaratorId ('=' variableInitializer)?
    ;
    
constantDeclaratorsRest
    :   constantDeclaratorRest (',' constantDeclarator)*
    ;

constantDeclaratorRest
    :   ('[' ']')* '=' variableInitializer
    ;
    
variableDeclaratorId
    :   Identifier ('[' ']')*{
    	parameterNames.add($Identifier.text);

    	if(isDataMember == true){
		System.out.println("2 " + accessModifierDataMember +$Identifier.text);
	    	temp += accessModifierDataMember + " " + returnTypeDataMember + " " +$Identifier.text ;

		
		 if(!hm_class_variables.containsKey(className)){
	                hm_class_variables.put(className,new ArrayList());
	               hm_class_variables_2.put(className,new ArrayList());
	                
	         }
	         hm_class_variables_2.get(className).add(new Variable(returnTypeDataMember, accessModifierDataMember, $Identifier.text));
	         hm_class_variables.get(className).add(temp);
		
		//dataMembers.add(temp);
		//myBuffer.add(temp);
		//resetting the variables
		temp = "";
		accessModifierDataMember="";
		returnTypeDataMember="";
		isDataMember=false;
    	}
    	
    	if(isLocalVariable == true){
	    	
	    	temp += returnTypeDataMember + " " +$Identifier.text ;
		
		 if(!hm_class_localvariable.containsKey(className)){
	                hm_class_localvariable.put(className,new ArrayList());
	         }
	         hm_class_localvariable.get(className).add(temp);

		temp = "";
		accessModifierDataMember="";
		returnTypeDataMember="";
		isLocalVariable=false;
    	}
    	
    }
    ;

variableInitializer
    :   arrayInitializer
    |   expression
    ;
        
arrayInitializer
    :   '{' (variableInitializer (',' variableInitializer)* (',')? )? '}'
    ;

modifier
    :   annotation
    |   'public' {
    	if(isMethod==true && accessModifierMethod.length()>0 && !accessModifierMethod.equalsIgnoreCase("public") && !accessModifierMethod.equalsIgnoreCase("private") && !accessModifierMethod.equalsIgnoreCase("protected")){
    		accessModifierMethod += " public";}
    	else{
    		accessModifierMethod="public";
    	}
    	
    	if(isDataMember==true && accessModifierDataMember.length()>0 && !accessModifierDataMember.equalsIgnoreCase("public") && !accessModifierDataMember.equalsIgnoreCase("private") && !accessModifierDataMember.equalsIgnoreCase("protected")){
    		accessModifierDataMember += " public";}
    	else{
    		accessModifierDataMember="public";
    	}  
    	}
    |   'protected'{accessModifierMethod="protected";accessModifierDataMember = "protected";}
    |   'private'{accessModifierMethod="private"; accessModifierDataMember = "private";}
    |   'static' {if(!isLocalVariable){accessModifierMethod="static"; accessModifierDataMember = "static";}}
    |   'abstract'{
    		
    		if(accessModifierMethod.length()>0){
    		
    		accessModifierMethod += " abstract ";}
	    	else{
	    		accessModifierMethod="abstract";
	    	}
    	//accessModifierMethod="abstract"; accessModifierDataMember = "abstract";
    	}
    |   'final'{accessModifierMethod="final"; accessModifierDataMember = "final";}
    |   'native'
    |   'synchronized'
    |   'transient'
    |   'volatile'
    |   'strictfp'
    ;

packageOrTypeName
    :   qualifiedName
    ;

enumConstantName
    :   Identifier
    ;

typeName
    :   qualifiedName
    ;

type
	:	{isLocalVariable=true;}classOrInterfaceType ('[' ']')*
	|	{if(isAggregate)isAggregate=false;if(isAssociate)isAssociate=false;}primitiveType ('[' ']')*
	;

classOrInterfaceType
	:	I1=Identifier {
				if(isExtends){
				//System.out.println($I1.text);
				  ancestor.put(className,$I1.text);
				  if(!descendent.containsKey($I1.text)){
	                       	  	descendent.put($I1.text,new HashSet());
	                       	  }
	                       	  descendent.get($I1.text).add(className);
				  myBuffer.add(className+" "+"extends"+" "+$I1.text);
				  isExtends=false;
	                          } 
	                       else 
	                       if(isImp){
	                       	  isExtends=false;
	                       	  isImp=false;
	                       } 
	                       
	                       if(isAggregate){
	                       	  isAggregate=false;
	                       	  if(!hm_class_aggregates.containsKey(className)){
	                       	  	hm_class_aggregates.put(className,new HashSet());
	                       	  
	                       	  }
	                       	  hm_class_aggregates.get(className).add($I1.text);
	                       	  
	  			  
	                       }
	                       
	                       if(isAssociate){
	                       
	                       	  isAssociate=false;
	                       	  if(!hm_class_associates.containsKey(className)){
	                       	  	hm_class_associates.put(className,new HashSet());
	                       	  
	                       	  }
	                       	  hm_class_associates.get(className).add($I1.text);
	                       	  isMain=false;
	  			  
	                       }
	                       
	                       if(isLocalVariable){
	                       	  returnTypeDataMember = $I1.text;
	                       }
	                       }
	         typeArguments? ('.' Identifier typeArguments? )*
	;

primitiveType
    :   'boolean'{if(isMethod)returnType = "boolean"; if(isParameter)parameterType.add("boolean"); if(isDataMember)returnTypeDataMember="boolean";}
    |   'char'{if(isMethod)returnType = "char"; if(isParameter)parameterType.add("char"); if(isDataMember)returnTypeDataMember="char";}
    |   'byte'{if(isMethod)returnType = "byte"; if(isParameter)parameterType.add("byte"); if(isDataMember)returnTypeDataMember="byte";}
    |   'short'{if(isMethod)returnType = "short"; if(isParameter)parameterType.add("short"); if(isDataMember)returnTypeDataMember="short";}
    |   'int' {if(isMethod)returnType = "int"; if(isParameter)parameterType.add("int"); if(isDataMember)returnTypeDataMember="int";}
    |   'long'{if(isMethod)returnType = "long"; if(isParameter)parameterType.add("long"); if(isDataMember)returnTypeDataMember="long";}
    |   'float'{if(isMethod)returnType = "float"; if(isParameter)parameterType.add("float"); if(isDataMember)returnTypeDataMember="float";}
    |   'double'{if(isMethod)returnType = "double"; if(isParameter)parameterType.add("double"); if(isDataMember)returnTypeDataMember="double";}
    |   'void' {if(isMethod)returnType = "void"; if(isParameter)parameterType.add("void"); if(isDataMember)returnTypeDataMember="void";}
    |   'String' {if(isMethod)returnType = "String"; if(isParameter)parameterType.add("String"); if(isDataMember)returnTypeDataMember="String";}
    ;

variableModifier
    :   'final'
    |   annotation
    ;

typeArguments
    :   '<' typeArgument (',' typeArgument)* '>'
    ;
    
typeArgument
    :   type
    |   '?' (('extends' | 'super') type)?
    ;
    
qualifiedNameList
    :   qualifiedName (',' qualifiedName)*
    ;

formalParameters
    :   {isParameter = true;isMethod=false; isDataMember=false;}'(' formalParameterDecls? ')'{isParameter = false;}
    ;
    
formalParameterDecls
    :   variableModifiers {isAssociate=true;}type formalParameterDeclsRest
    ;
    
formalParameterDeclsRest
    :   variableDeclaratorId (',' formalParameterDecls)?
    |   '...' variableDeclaratorId
    ;
    
methodBody
    :   block
    ;

constructorBody
    :   '{' explicitConstructorInvocation? blockStatement* '}'
    ;

explicitConstructorInvocation
    :   nonWildcardTypeArguments? ('this' | 'super') arguments ';'
    |   primary '.' nonWildcardTypeArguments? 'super' arguments ';'
    ;


qualifiedName
    :   Identifier ('.' Identifier)* 
    ;
    
literal 
    :   integerLiteral
    |   FloatingPointLiteral
    |   CharacterLiteral
    |   StringLiteral
    |   booleanLiteral
    |   'null'
    ;

integerLiteral
    :   HexLiteral
    |   OctalLiteral
    |   DecimalLiteral
    ;

booleanLiteral
    :   'true'
    |   'false'
    ;

// ANNOTATIONS

annotations
    :   annotation+
    ;

annotation
    :   '@' annotationName ( '(' ( elementValuePairs | elementValue )? ')' )?
    ;
    
annotationName
    : Identifier ('.' Identifier)*
    ;

elementValuePairs
    :   elementValuePair (',' elementValuePair)*
    ;

elementValuePair
    :   Identifier '=' elementValue
    ;
    
elementValue
    :   conditionalExpression
    |   annotation
    |   elementValueArrayInitializer
    ;
    
elementValueArrayInitializer
    :   '{' (elementValue (',' elementValue)*)? (',')? '}'
    ;
    
annotationTypeDeclaration
    :   '@' 'interface' Identifier annotationTypeBody
    ;
    
annotationTypeBody
    :   '{' (annotationTypeElementDeclaration)* '}'
    ;
    
annotationTypeElementDeclaration
    :   modifiers annotationTypeElementRest
    ;
    
annotationTypeElementRest
    :   type annotationMethodOrConstantRest ';'
    |   normalClassDeclaration ';'?
    |   normalInterfaceDeclaration ';'?
    |   enumDeclaration ';'?
    |   annotationTypeDeclaration ';'?
    ;
    
annotationMethodOrConstantRest
    :   annotationMethodRest
    |   annotationConstantRest
    ;
    
annotationMethodRest
    :   Identifier '(' ')' defaultValue?
    ;
    
annotationConstantRest
    :   variableDeclarators
    ;
    
defaultValue
    :   'default' elementValue
    ;

// STATEMENTS / BLOCKS

block
    :   '{' blockStatement* '}'
    ;
    
blockStatement
    :   localVariableDeclarationStatement
    |   classOrInterfaceDeclaration
    |   statement
    ;
    
localVariableDeclarationStatement
    :    localVariableDeclaration ';'
    ;

localVariableDeclaration
    :   variableModifiers type variableDeclarators
    ;
    
variableModifiers
    :   variableModifier*
    ;

statement
    : block
    |   ASSERT expression (':' expression)? ';'
    |   'if' parExpression statement (options {k=1;}:'else' statement)?
    |   'for' '(' forControl ')' statement
    |   'while' parExpression statement
    |   'do' statement 'while' parExpression ';'
    |   'try' block
        ( catches 'finally' block
        | catches
        |   'finally' block
        )
    |   'switch' parExpression '{' switchBlockStatementGroups '}'
    |   'synchronized' parExpression block
    |   'return' expression? ';'
    |   'throw' expression ';'
    |   'break' Identifier? ';'
    |   'continue' Identifier? ';'
    |   ';' 
    |   statementExpression ';'
    |   Identifier ':' statement
    ;
    
catches
    :   catchClause (catchClause)*
    ;
    
catchClause
    :   'catch' '(' formalParameter ')' block
    ;

formalParameter
    :   variableModifiers type variableDeclaratorId
    ;
        
switchBlockStatementGroups
    :   (switchBlockStatementGroup)*
    ;
    
/* The change here (switchLabel -> switchLabel+) technically makes this grammar
   ambiguous; but with appropriately greedy parsing it yields the most
   appropriate AST, one in which each group, except possibly the last one, has
   labels and statements. */
switchBlockStatementGroup
    :   switchLabel+ blockStatement*
    ;
    
switchLabel
    :   'case' constantExpression ':'
    |   'case' enumConstantName ':'
    |   'default' ':'
    ;
    
forControl
options {k=3;} // be efficient for common case: for (ID ID : ID) ...
    :   enhancedForControl
    |   forInit? ';' expression? ';' forUpdate?
    ;

forInit
    :   localVariableDeclaration
    |   expressionList
    ;
    
enhancedForControl
    :   variableModifiers type Identifier ':' expression
    ;

forUpdate
    :   expressionList
    ;

// EXPRESSIONS

parExpression
    :   '(' expression ')'
    ;
    
expressionList
    :   expression (',' expression)*
    ;

statementExpression
    :   expression
    ;
    
constantExpression
    :   expression
    ;
    
expression
    :   conditionalExpression (assignmentOperator expression)?
    ;
    
assignmentOperator
    :   '='
    |   '+='
    |   '-='
    |   '*='
    |   '/='
    |   '&='
    |   '|='
    |   '^='
    |   '%='
    |   ('<' '<' '=')=> t1='<' t2='<' t3='=' 
        { $t1.getLine() == $t2.getLine() &&
          $t1.getCharPositionInLine() + 1 == $t2.getCharPositionInLine() && 
          $t2.getLine() == $t3.getLine() && 
          $t2.getCharPositionInLine() + 1 == $t3.getCharPositionInLine() }?
    |   ('>' '>' '>' '=')=> t1='>' t2='>' t3='>' t4='='
        { $t1.getLine() == $t2.getLine() && 
          $t1.getCharPositionInLine() + 1 == $t2.getCharPositionInLine() &&
          $t2.getLine() == $t3.getLine() && 
          $t2.getCharPositionInLine() + 1 == $t3.getCharPositionInLine() &&
          $t3.getLine() == $t4.getLine() && 
          $t3.getCharPositionInLine() + 1 == $t4.getCharPositionInLine() }?
    |   ('>' '>' '=')=> t1='>' t2='>' t3='='
        { $t1.getLine() == $t2.getLine() && 
          $t1.getCharPositionInLine() + 1 == $t2.getCharPositionInLine() && 
          $t2.getLine() == $t3.getLine() && 
          $t2.getCharPositionInLine() + 1 == $t3.getCharPositionInLine() }?
    ;

conditionalExpression
    :   conditionalOrExpression ( '?' expression ':' expression )?
    ;

conditionalOrExpression
    :   conditionalAndExpression ( '||' conditionalAndExpression )*
    ;

conditionalAndExpression
    :   inclusiveOrExpression ( '&&' inclusiveOrExpression )*
    ;

inclusiveOrExpression
    :   exclusiveOrExpression ( '|' exclusiveOrExpression )*
    ;

exclusiveOrExpression
    :   andExpression ( '^' andExpression )*
    ;

andExpression
    :   equalityExpression ( '&' equalityExpression )*
    ;

equalityExpression
    :   instanceOfExpression ( ('==' | '!=') instanceOfExpression )*
    ;

instanceOfExpression
    :   relationalExpression ('instanceof' type)?
    ;

relationalExpression
    :   shiftExpression ( relationalOp shiftExpression )*
    ;
    
relationalOp
    :   ('<' '=')=> t1='<' t2='=' 
        { $t1.getLine() == $t2.getLine() && 
          $t1.getCharPositionInLine() + 1 == $t2.getCharPositionInLine() }?
    |   ('>' '=')=> t1='>' t2='=' 
        { $t1.getLine() == $t2.getLine() && 
          $t1.getCharPositionInLine() + 1 == $t2.getCharPositionInLine() }?
    |   '<' 
    |   '>' 
    ;

shiftExpression
    :   additiveExpression ( shiftOp additiveExpression )*
    ;

shiftOp
    :   ('<' '<')=> t1='<' t2='<' 
        { $t1.getLine() == $t2.getLine() && 
          $t1.getCharPositionInLine() + 1 == $t2.getCharPositionInLine() }?
    |   ('>' '>' '>')=> t1='>' t2='>' t3='>' 
        { $t1.getLine() == $t2.getLine() && 
          $t1.getCharPositionInLine() + 1 == $t2.getCharPositionInLine() &&
          $t2.getLine() == $t3.getLine() && 
          $t2.getCharPositionInLine() + 1 == $t3.getCharPositionInLine() }?
    |   ('>' '>')=> t1='>' t2='>'
        { $t1.getLine() == $t2.getLine() && 
          $t1.getCharPositionInLine() + 1 == $t2.getCharPositionInLine() }?
    ;


additiveExpression
    :   multiplicativeExpression ( ('+' | '-') multiplicativeExpression )*
    ;

multiplicativeExpression
    :   unaryExpression ( ( '*' | '/' | '%' ) unaryExpression )*
    ;
    
unaryExpression
    :   '+' unaryExpression
    |   '-' unaryExpression
    |   '++' unaryExpression
    |   '--' unaryExpression
    |   unaryExpressionNotPlusMinus
    ;

unaryExpressionNotPlusMinus
    :   '~' unaryExpression
    |   '!' unaryExpression
    |   castExpression
    |   primary selector* ('++'|'--')?
    ;

castExpression
    :  '(' primitiveType ')' unaryExpression
    |  '(' (type | expression) ')' unaryExpressionNotPlusMinus
    ;

primary
    :   parExpression
    |   'this' ('.' Identifier)* identifierSuffix?
    |   'super' superSuffix
    |   literal
    |   {isAssociate = true;}'new' creator
    |   Identifier ('.' Identifier)* identifierSuffix?
    |   primitiveType ('[' ']')* '.' 'class'
    |   'void' '.' 'class'
    ;

identifierSuffix
    :   ('[' ']')+ '.' 'class'
    |   ('[' expression ']')+ // can also be matched by selector, but do here
    |   arguments
    |   '.' 'class'
    |   '.' explicitGenericInvocation
    |   '.' 'this'
    |   '.' 'super' arguments
    |   '.' 'new' innerCreator
    ;

creator
    :   nonWildcardTypeArguments createdName classCreatorRest
    |   createdName (arrayCreatorRest | classCreatorRest)
    ;

createdName
    :   classOrInterfaceType
    |   primitiveType
    ;
    
innerCreator
    :   nonWildcardTypeArguments? Identifier classCreatorRest
    ;

arrayCreatorRest
    :   '['
        (   ']' ('[' ']')* arrayInitializer
        |   expression ']' ('[' expression ']')* ('[' ']')*
        )
    ;

classCreatorRest
    :   arguments classBody?
    ;
    
explicitGenericInvocation
    :   nonWildcardTypeArguments Identifier arguments
    ;
    
nonWildcardTypeArguments
    :   '<' typeList '>'
    ;
    
selector
    :   '.' Identifier arguments?
    |   '.' 'this'
    |   '.' 'super' superSuffix
    |   '.' 'new' innerCreator
    |   '[' expression ']'
    ;
    
superSuffix
    :   arguments
    |   '.' Identifier arguments?
    ;

arguments
    :   '(' expressionList? ')'
    ;

// LEXER

HexLiteral : '0' ('x'|'X') HexDigit+ IntegerTypeSuffix? ;

DecimalLiteral : ('0' | '1'..'9' '0'..'9'*) IntegerTypeSuffix? ;

OctalLiteral : '0' ('0'..'7')+ IntegerTypeSuffix? ;

fragment
HexDigit : ('0'..'9'|'a'..'f'|'A'..'F') ;

fragment
IntegerTypeSuffix : ('l'|'L') ;

FloatingPointLiteral
    :   ('0'..'9')+ '.' ('0'..'9')* Exponent? FloatTypeSuffix?
    |   '.' ('0'..'9')+ Exponent? FloatTypeSuffix?
    |   ('0'..'9')+ Exponent FloatTypeSuffix?
    |   ('0'..'9')+ FloatTypeSuffix
    ;

fragment
Exponent : ('e'|'E') ('+'|'-')? ('0'..'9')+ ;

fragment
FloatTypeSuffix : ('f'|'F'|'d'|'D') ;

CharacterLiteral
    :   '\'' ( EscapeSequence | ~('\''|'\\') ) '\''
    ;

StringLiteral
    :  '"' ( EscapeSequence | ~('\\'|'"') )* '"'
    ;

fragment
EscapeSequence
    :   '\\' ('b'|'t'|'n'|'f'|'r'|'\"'|'\''|'\\')
    |   UnicodeEscape
    |   OctalEscape
    ;

fragment
OctalEscape
    :   '\\' ('0'..'3') ('0'..'7') ('0'..'7')
    |   '\\' ('0'..'7') ('0'..'7')
    |   '\\' ('0'..'7')
    ;

fragment
UnicodeEscape
    :   '\\' 'u' HexDigit HexDigit HexDigit HexDigit
    ;

ENUM:   'enum' {if (!enumIsKeyword) $type=Identifier;}
    ;
    
ASSERT
    :   'assert' {if (!assertIsKeyword) $type=Identifier;}
    ;
    
Identifier 
    :   Letter (Letter|JavaIDDigit)*
    ;

/**I found this char range in JavaCC's grammar, but Letter and Digit overlap.
   Still works, but...
 */
fragment
Letter
    :  '\u0024' |
       '\u0041'..'\u005a' |
       '\u005f' |
       '\u0061'..'\u007a' |
       '\u00c0'..'\u00d6' |
       '\u00d8'..'\u00f6' |
       '\u00f8'..'\u00ff' |
       '\u0100'..'\u1fff' |
       '\u3040'..'\u318f' |
       '\u3300'..'\u337f' |
       '\u3400'..'\u3d2d' |
       '\u4e00'..'\u9fff' |
       '\uf900'..'\ufaff'
    ;

fragment
JavaIDDigit
    :  '\u0030'..'\u0039' |
       '\u0660'..'\u0669' |
       '\u06f0'..'\u06f9' |
       '\u0966'..'\u096f' |
       '\u09e6'..'\u09ef' |
       '\u0a66'..'\u0a6f' |
       '\u0ae6'..'\u0aef' |
       '\u0b66'..'\u0b6f' |
       '\u0be7'..'\u0bef' |
       '\u0c66'..'\u0c6f' |
       '\u0ce6'..'\u0cef' |
       '\u0d66'..'\u0d6f' |
       '\u0e50'..'\u0e59' |
       '\u0ed0'..'\u0ed9' |
       '\u1040'..'\u1049'
   ;

WS  :  (' '|'\r'|'\t'|'\u000C'|'\n') {$channel=HIDDEN;}
    ;

COMMENT
    :   '/*' ( options {greedy=false;} : . )* '*/' {$channel=HIDDEN;}
    ;

LINE_COMMENT
    : '//' ~('\n'|'\r')* '\r'? '\n' {$channel=HIDDEN;}
    ;
