COMPILER = kotlinc-native

LIBNAME  = src/file
KLIB     = klib/file

all:
	@mkdir -p klib
	$(COMPILER) $(LIBNAME).kt -p library -o $(KLIB)
	klib install $(KLIB)
