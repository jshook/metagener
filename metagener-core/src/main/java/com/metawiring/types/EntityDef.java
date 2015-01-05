/*
*   Copyright 2014 Jonathan Shook
*   Licensed under the Apache License, Version 2.0 (the "License");
*   you may not use this file except in compliance with the License.
*   You may obtain a copy of the License at
*
*       http://www.apache.org/licenses/LICENSE-2.0
*
*   Unless required by applicable law or agreed to in writing, software
*   distributed under the License is distributed on an "AS IS" BASIS,
*   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*   See the License for the specific language governing permissions and
*   limitations under the License.
*/

package com.metawiring.types;

import java.util.List;

/**
 * A description of an entity to generate
 */
public interface EntityDef {

    /**
     * Get the name of the entity.
     * @return entity name
     */
    public String getName();

    /**
     * Get the population size of the entity.
     * @return population size
     */
    public long getPopulationSize();

    /**
     * Get the field definitions, in the order that they were added
     * @return field definitions list
     */
    public List<FieldDef> getFieldDefs();

    /**
     * Get the field definition for the named field, or throw an exception
     * if it doesn't exist.
     * @param fieldName The name of the field def to get
     * @throws RuntimeException if the named field does not exist
     * @return a FieldDef for fieldName
     */
    public FieldDef getFieldDef(String fieldName);

    /**
     * Returns the smallest population value.
     * @return smallest entity id
     */
    default public long getMinId() {
        return 0;
    }

    /**
     * Returns the largest possible population value.
     * @return largest entity id
     */
    default public long getMaxId() {
        return getPopulationSize();
    }
}
