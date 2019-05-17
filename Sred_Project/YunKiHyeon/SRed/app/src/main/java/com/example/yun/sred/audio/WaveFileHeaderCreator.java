/**
 *
 */
package com.example.yun.sred.audio;

import android.media.AudioFormat;

import java.io.IOException;
import java.io.OutputStream;

/**
 * via http://www.kk.iij4u.or.jp/~kondo/wave/index.html
 */
public class WaveFileHeaderCreator {

	public static void pushWaveHeader(OutputStream target,
			int sampleRate, int channelConfig, int audioEncoding, int size) throws IOException {
		boolean is8bit = audioEncoding == AudioFormat.ENCODING_PCM_8BIT;
		boolean isMonoChannel = channelConfig != AudioFormat.CHANNEL_CONFIGURATION_STEREO;

		target.write("RIFF".getBytes());
		pushInt(target, size + 36);
		target.write("WAVEfmt ".getBytes());
		pushInt(target, (is8bit ? 8 : 16));
		pushShort(target, 1);
		pushShort(target, isMonoChannel ? 1 : 2);
		pushInt(target, sampleRate);
		pushInt(target, sampleRate * (isMonoChannel ? 1 : 2) * (is8bit ? 1 : 2));
		pushShort(target, (isMonoChannel ? 1 : 2) * (is8bit ? 1 : 2));
		pushShort(target, is8bit ? 8 : 16);
		target.write("data".getBytes());
		pushInt(target, size);
	}

	private static void pushInt(OutputStream stream, int value) throws IOException {
		stream.write(0xff & value);
		stream.write(0xff & (value >> 8));
		stream.write(0xff & (value >> 16));
		stream.write(0xff & (value >> 24));
	}
	private static void pushShort(OutputStream stream, int value) throws IOException {
		stream.write(0xff & value);
		stream.write(0xff & (value >> 8));
	}
}
