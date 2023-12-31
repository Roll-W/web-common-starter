/*
 * Copyright (C) 2023 RollW
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package tech.rollw.common.web.system;

import space.lingu.NonNull;

/**
 * @author RollW
 */
public interface SystemResourceOperatorFactory<ID> extends SystemResourceSupportable {
    @Override
    boolean supports(@NonNull SystemResourceKind systemResourceKind);

    @NonNull
    SystemResourceOperator<ID> createResourceOperator(
            SystemResource<ID> systemResource,
            boolean checkDelete
    );

    @NonNull
    default SystemResourceOperator<ID> createResourceOperator(@NonNull SystemResource<ID> systemResource) {
        return createResourceOperator(systemResource, true);
    }

    /**
     * Open a new resource operator through its related resource.
     * Such as using a user to open a user-setting operator.
     * <p>
     * Will check the target system resource kind is supported by
     * this factory.
     */
    @NonNull
    default SystemResourceOperator<ID> createResourceOperator(
            @NonNull SystemResource<ID> systemResource,
            @NonNull  SystemResourceKind targetSystemResourceKind,
            boolean checkDelete
    ) {
        throw new UnsupportedOperationException();
    }

    @NonNull
    default SystemResourceOperator<ID> createResourceOperator(
            @NonNull SystemResource<ID> systemResource,
            @NonNull SystemResourceKind targetSystemResourceKind
    ) {
        return createResourceOperator(systemResource, targetSystemResourceKind, true);
    }
}
