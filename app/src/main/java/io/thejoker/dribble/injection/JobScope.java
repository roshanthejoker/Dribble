package io.thejoker.dribble.injection;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import javax.inject.Scope;

/**
 * Created by thejoker on 6/10/16.
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface JobScope {
}
