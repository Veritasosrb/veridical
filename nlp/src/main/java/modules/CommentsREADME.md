

     CreateNlpPipeline Comments README file

- Lines 61-64: The toString() method on an Annotation just prints the text of the Annotation. The toShorterString() method Attempt to provide a briefer and more human readable String for the contents of a CoreMap. It prints entire text, tokens present in it and its position in text. Prints entity mentions and coref chains.


- Line 72: An Annotation is a Map with Class keys for the linguistic analysis types. You can get and use the various analyzes individually. CoreAnnotations.SentencesAnnotation.class is the CoreMap key for getting the sentences contained by an annotation. CoreMap is base type for all annotatable core objects.


- Lines 76-80: It returns the plain text,tokens present in it,start and end position of sentence,sentence index. Also returns Normal Dependencies,Basic Dependencies,Collapsed Dependencies,Enhanced Dependencies,Enhanced plus plus dependencies,KBP Triples,Sentiment annotated tree.


- Line 99: Basic dependencies present in sentence.The basic typed dependencies are all binary relations: a grammatical relation holds between a governor (also known as a regent or a head) and a dependent.Each word in the sentence is the dependent of exactly one thing, either another word in the sentence or the distinguished �ROOT-0� token. 
For Example: nsubj-A nominal subject is a noun phrase which is the syntactic subject of a clause.


- Line 104: Enhanced dependencies present in sentence which contain additional and augmented relations that explicitly capture otherwise implicit relation between content word. The enhanced English UD representation aims to make implicit relations between content words more explicit by adding relations and augmenting relation names. This helps to disambiguate the type of modifier and further facilitates the extraction of relationships between content words, especially if a system incorporates dependency information using very simple methods such as by only considering paths between two nodes.


- Line 119 : This give some idea of how to navigate the dependency structure in a SemanticGraph IndexedWord class provides a CoreLabel that uses itsDocIDAnnotation, SentenceIndexAnnotation, and IndexAnnotation to implementComparable/compareTo, hashCode, and equals. getNodeByIndexSafe() returns the first IndexedWord in this SemanticGraph having the given integer index and returns null if the index does not exist.


     FrameStructureActive Comments README 

- Line 10-11:	First regular expression consist of compulsory dependency called as nsubj-nominal subject (is a noun phrase which is the syntactic subject of a clause).The general format of active voice is subject(Noun)+main verb+object(NN). So created regular expression consisting noun+verb+noun to check whether sentence is in active voice or not. here .* is used to take into consideration the words that would occur in middle of structure.
    
    
     FrameStructurePassive Comments README      

- Line 9-10: First regular expression consist of compulsory dependency called as nsubjpass-passive nominal subject (is a noun phrase which is the syntactic subject of a passive clause).The general format of passive voice is <form of TO BE> <verb in PAST PRINCIPLE>.So created regular expression consisting verb(third person)+verb in past participle to check whether sentence is in passive voice or not here .* is used to take into consideration the words that would occur in middle of structure.


     FrameStructurePreposition Comments README      

- Line 9: First regular expression consist of compulsory dependency called as case is used for any case-marking element which is treated as a separate syntactic word (including prepositions, postpositions)

	 
     FrameStructureConjunction Comments README 
    
- Line 9-11: First regular expression consist of compulsory dependency called as cc coordination is the relation between an element of a conjunct and the coordinating conjunction word of the conjunct. Regex for the general format to check coordination


     FrameStructureMarker Comments README 

- Line 10 : First regular expression consist of compulsory dependency called as advmod is adverb modifier which tells about marks. An adverb modifier of a word is a (non-clausal) adverb or adverb-headed phrase that serves to modify the meaning of the word. Function consist of actual code to check the voice of sentence with parameters consist of enhanced plus plus dependency and pos tags of each sentence.

