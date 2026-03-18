# AGENTS.md

## Cursor Cloud specific instructions

This repository is a **document-only repository** containing PDF lecture materials and Java cryptography lab files for a French-language university course on networking, security, and cryptography.

### Repository structure

- Root directory: PDF lecture slides, exam papers, and reference documents.
- `Documents_chiffrement-avec-java/`: Contains a PDF lab guide and a `fichiers-java.zip` archive with standalone Java example programs.

### Runnable content

The only executable content is inside `Documents_chiffrement-avec-java/Fichiers-de-travail_2025/fichiers-java.zip`. To work with the Java examples:

1. Extract: `unzip Documents_chiffrement-avec-java/Fichiers-de-travail_2025/fichiers-java.zip -d /tmp/java-examples`
2. Compile standard examples (7 of 9 use only JDK APIs): `javac *.java` (excluding `KEMExample.java` and `SigExample.java` which require the external `liboqs-java` / Open Quantum Safe library)
3. Run examples: `java <ClassName> "text"` (most take a single text argument)

### Important notes

- **No build system, package manager, or application server** exists in this repo. There is no `package.json`, `pom.xml`, `build.gradle`, `Makefile`, or `Dockerfile`.
- **No lint, test, or build commands** are defined. The Java files are standalone programs compiled directly with `javac`.
- **Java 21** (OpenJDK) is available in the environment and is sufficient for all standard examples.
- `KEMExample.java` and `SigExample.java` require the `org.openquantumsafe` library (liboqs-java), which is not installed by default.
