/**
 * Copyright (C) 2018 Dmitry Moryakov, Main aeronautical meteorological center, Moscow, Russia
 * moryakovdv[at]gmail[dot]com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package rest;

import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.core.Application;

/**registers all rest service classes in application
 * */

public class IwxxmRestApplication extends Application {
	private Set<Object> singletons = new HashSet<Object>();

	public IwxxmRestApplication() {
		singletons.add(new IwxxmRestConverter());
		
	}

	@Override
	public Set<Object> getSingletons() {
		return singletons;
	}
}

