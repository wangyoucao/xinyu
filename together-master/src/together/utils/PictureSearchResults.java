/*
 * Copyright 2010-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package together.utils;

import java.util.List;

import together.models.Picture;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


/**
 * @author Roy Clarkson
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PictureSearchResults {

	private List<Picture> results;

	public void setResults(List<Picture> results) {
		this.results = results;
	}

	public List<Picture> getResults() {
		return this.results;
	}

}
