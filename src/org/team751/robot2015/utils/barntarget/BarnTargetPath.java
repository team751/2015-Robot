package org.team751.robot2015.utils.barntarget;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by sambaumgarten on 3/26/15
 */
public class BarnTargetPath {
	private ArrayList<BarnTargetPathSegment>	segments;

	public BarnTargetPath(String file) throws IOException {
		this.segments = new ArrayList<>();

		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] segmentData = line.split(",");
				BarnTargetPathSegment s = new BarnTargetPathSegment(Double.parseDouble(segmentData[6]), Double.parseDouble(segmentData[0]), Double.parseDouble(segmentData[2]), Double.parseDouble(segmentData[1]), Double.parseDouble(segmentData[3]), Double.parseDouble(segmentData[4]), Double.parseDouble(segmentData[5]), Double.parseDouble(segmentData[7]), Boolean.parseBoolean(segmentData[8]));
				this.segments.add(s);
			}
		}
	}

	public ArrayList<BarnTargetPathSegment> getSegments() {
		return this.segments;
	}
}
