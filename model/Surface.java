package model;

import linalg.Float3;
import linalg.Ray;

public interface Surface
{
	float getDist( Float3 pos );
	Material getMat();
}
