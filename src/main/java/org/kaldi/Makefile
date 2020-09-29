# 编译kaldi,可以看https://alphacephei.com/vosk/install。除了一般kaldi编译方式之外，还需要在编译时指定mathlib=OPENBLAS。
# `export KALDI_ROOT=<KALDI_ROOT>`
# `export JAVA_HOME=<JAVA_HOME>`
# `make`
#将生成的libvosk_jni.so复制到Djava.library.path,从https://alphacephei.com/vosk/install下载中文的语音识别模型。
KALDI_ROOT ?= $(HOME)/kaldi
CFLAGS := -g -O2 -DPIC -fPIC -Wno-unused-function
CPPFLAGS := -I$(JAVA_HOME)/include -I$(JAVA_HOME)/include/linux -I$(KALDI_ROOT)/src -I$(KALDI_ROOT)/tools/openfst/include -I../src

KALDI_LIBS = \
             ${KALDI_ROOT}/src/online2/kaldi-online2.a \
             ${KALDI_ROOT}/src/decoder/kaldi-decoder.a \
             ${KALDI_ROOT}/src/ivector/kaldi-ivector.a \
             ${KALDI_ROOT}/src/gmm/kaldi-gmm.a \
             ${KALDI_ROOT}/src/nnet3/kaldi-nnet3.a \
             ${KALDI_ROOT}/src/tree/kaldi-tree.a \
             ${KALDI_ROOT}/src/feat/kaldi-feat.a \
             ${KALDI_ROOT}/src/lat/kaldi-lat.a \
             ${KALDI_ROOT}/src/lm/kaldi-lm.a \
             ${KALDI_ROOT}/src/hmm/kaldi-hmm.a \
             ${KALDI_ROOT}/src/transform/kaldi-transform.a \
             ${KALDI_ROOT}/src/cudamatrix/kaldi-cudamatrix.a \
             ${KALDI_ROOT}/src/matrix/kaldi-matrix.a \
             ${KALDI_ROOT}/src/fstext/kaldi-fstext.a \
             ${KALDI_ROOT}/src/util/kaldi-util.a \
             ${KALDI_ROOT}/src/base/kaldi-base.a \
             ${KALDI_ROOT}/tools/openfst/lib/libfst.a \
             ${KALDI_ROOT}/tools/openfst/lib/libfstngram.a \
             ${KALDI_ROOT}/tools/OpenBLAS/libopenblas.a \
             -lgfortran

all: libvosk_jni.so

VOSK_SOURCES = \
	vosk_wrap.cc \
	kaldi_recognizer.cc \
	kaldi_recognizer.h \
	model.cc \
	model.h \
	spk_model.cc \
	spk_model.h \
	vosk_api.cc \
	vosk_api.h

libvosk_jni.so: $(VOSK_SOURCES)
	$(CXX) -std=c++11   -shared -o $@ $(CPPFLAGS) $(CFLAGS) $(VOSK_SOURCES) $(KALDI_LIBS)

# vosk_wrap.cc: vosk.i
# 	mkdir -p org/kaldi
# 	swig -c++ -I src \
# 		-java -package org.kaldi \
# 		-outdir org/kaldi -o $@ $<

clean:
	$(RM) *.so *_wrap.cc *_wrap.o test/*.class
	$(RM) -r org model-en

# model:
# 	wget https://alphacephei.com/kaldi/models/vosk-model-small-en-us-0.3.zip
# 	unzip vosk-model-small-en-us-0.3.zip && rm vosk-model-small-en-us-0.3.zip
# 	mv vosk-model-small-en-us-0.3 model
#
# model-spk:
# 	wget https://alphacephei.com/kaldi/models/vosk-model-spk-0.3.zip
# 	unzip vosk-model-spk-0.3.zip && rm vosk-model-spk-0.3.zip
# 	mv vosk-model-spk-0.3 model-spk

#run: model model-spk
#	javac test/*.java org/kaldi/*.java
#	java -Djava.library.path=. -cp . test.DecoderTest