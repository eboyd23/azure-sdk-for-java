/* JUG Java Uuid Generator
 *
 * Copyright (c) 2002- Tatu Saloranta, tatu.saloranta@iki.fi
 *
 * Licensed under the License specified in the file LICENSE which is
 * included with the source code.
 * You may not use this file except in compliance with the License.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/*
 * Portions Copyright (c) Microsoft Corporation
 */

package com.azure.cosmos.implementation.uuid;

import java.util.UUID;

/**
 * Intermediate base class for UUID generators that do not take arguments for individual
 * calls. This includes random and time-based variants, but not name-based ones.
 * 
 * @since 3.0
 */
public abstract class NoArgGenerator extends UUIDGenerator
{
    public abstract UUID generate();
}
