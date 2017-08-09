/*
 * Copyright 2017 Google Inc. All Rights Reserved.
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

package com.google.cloud.tools.intellij.collect;

import java.util.Collections;
import java.util.Set;
import java.util.StringJoiner;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

/**
 * A {@link Collector} that joins the elements in a stream of strings to be separated by paragraph
 * tags.
 *
 * <p>For example:
 *
 * <pre>{@code
 * Stream.of("Paragraph 1", "Paragraph 2").collect(toParagraphs());
 * }</pre>
 *
 * <p>... results in the string "{@literal <p>Paragraph 1</p><p>Paragraph 2</p>}".
 */
public final class ParagraphCollector implements Collector<String, StringJoiner, String> {

  private static final String START = "<p>";
  private static final String END = "</p>";

  /** Returns a new {@link ParagraphCollector}. */
  public static ParagraphCollector toParagraphs() {
    return new ParagraphCollector();
  }

  @Override
  public Supplier<StringJoiner> supplier() {
    return () -> {
      StringJoiner joiner = new StringJoiner(END + START, START, END);
      joiner.setEmptyValue("");
      return joiner;
    };
  }

  @Override
  public BiConsumer<StringJoiner, String> accumulator() {
    return StringJoiner::add;
  }

  @Override
  public BinaryOperator<StringJoiner> combiner() {
    return StringJoiner::merge;
  }

  @Override
  public Function<StringJoiner, String> finisher() {
    return StringJoiner::toString;
  }

  @Override
  public Set<Characteristics> characteristics() {
    return Collections.emptySet();
  }
}
