/*
 *  @author Flavio Keller
 *
 *  Copyright 2014 University of Zurich
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package com.signalcollect.sna

import scala.collection.mutable.ArrayBuffer
import com.signalcollect.Vertex
import com.signalcollect.ExecutionInformation
/**
 * Class which is responsible to store the result of an execution of a sna method
 * it stores the actual computation results (see {@link ComputationResults}),
 * the array of vertices (which may be reused) and the stats of the execution
 */
class ExecutionResult(val compRes: ComputationResults, val vertexArray: ArrayBuffer[Vertex[Any, _,Any,Any]], val stats:ExecutionInformation[Any,Any]) {

}