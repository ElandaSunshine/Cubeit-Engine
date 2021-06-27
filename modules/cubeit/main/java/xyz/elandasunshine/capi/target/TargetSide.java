package xyz.elandasunshine.capi.target;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
public @interface TargetSide
{
	VmTarget value();
}
