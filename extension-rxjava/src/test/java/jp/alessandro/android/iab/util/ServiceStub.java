/*
 *  Copyright (C) 2016 Alessandro Yuichi Okimoto
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 *  Contact email: alessandro@alessandro.jp
 */

package jp.alessandro.android.iab.util;

import android.content.ComponentName;
import android.os.Bundle;
import android.os.RemoteException;

import com.android.vending.billing.IInAppBillingService;

import org.robolectric.RuntimeEnvironment;
import org.robolectric.shadows.ShadowApplication;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.robolectric.Shadows.shadowOf;

/**
 * Created by Alessandro Yuichi Okimoto on 2017/02/26.
 */

public class ServiceStub {

    public static final String CONSUME_PURCHASE = "consume_purchase";
    public static final String GET_BUY_INTENT = "get_buy_intent";
    public static final String GET_BUY_INTENT_TO_REPLACE_SKUS = "get_buy_intent_to_replace_skus";
    public static final String GET_PURCHASES = "get_purchases";
    public static final String GET_SKU_DETAILS = "get_sku_details";
    public static final String IN_APP_BILLING_SUPPORTED = "is_billing_supported";
    public static final String THROW_REMOTE_EXCEPTION_ON_GET_ACTIONS = "throw_remote_exception_on_get_actions";
    public static final String THROW_REMOTE_EXCEPTION_ON_CONSUME_PURCHASE = "throw_remote_exception_on_consume_purchase";
    public static final String THROW_REMOTE_EXCEPTION_ON_BILLING_SUPPORTED = "throw_remote_exception_on_billing_supported";

    public void setServiceForBinding(final Bundle stubBundle) {
        ShadowApplication shadowApplication = shadowOf(RuntimeEnvironment.application);
        IInAppBillingService.Stub stub = create(stubBundle);
        ComponentName cn = mock(ComponentName.class);
        shadowApplication.setComponentNameAndServiceForBindService(cn, stub);
    }

    @SuppressWarnings("checkstyle:methodlength")
    public IInAppBillingService.Stub create(final Bundle bundle) {
        return new IInAppBillingService.Stub() {
            @Override
            public int isBillingSupported(int apiVersion,
                                          String packageName,
                                          String type) throws RemoteException {

                if (bundle.getBoolean(THROW_REMOTE_EXCEPTION_ON_BILLING_SUPPORTED, false)) {
                    throw new RemoteException();
                }
                return bundle.getInt(IN_APP_BILLING_SUPPORTED, 0);
            }

            @Override
            public Bundle getSkuDetails(int apiVersion,
                                        String packageName,
                                        String type,
                                        Bundle skusBundle) throws RemoteException {

                if (bundle.getBoolean(THROW_REMOTE_EXCEPTION_ON_GET_ACTIONS, false)) {
                    throw new RemoteException();
                }
                return bundle.getParcelable(GET_SKU_DETAILS);
            }

            @Override
            public Bundle getBuyIntent(int apiVersion,
                                       String packageName,
                                       String sku,
                                       String type,
                                       String developerPayload) throws RemoteException {

                if (bundle.getBoolean(THROW_REMOTE_EXCEPTION_ON_GET_ACTIONS, false)) {
                    throw new RemoteException();
                }
                return bundle.getParcelable(GET_BUY_INTENT);
            }

            @Override
            public Bundle getPurchases(int apiVersion,
                                       String packageName,
                                       String type,
                                       String continuationToken) throws RemoteException {

                if (bundle.getBoolean(THROW_REMOTE_EXCEPTION_ON_GET_ACTIONS, false)) {
                    throw new RemoteException();
                }
                return bundle.getParcelable(GET_PURCHASES);
            }

            @Override
            public int consumePurchase(int apiVersion,
                                       String packageName,
                                       String purchaseToken) throws RemoteException {

                if (bundle.getBoolean(THROW_REMOTE_EXCEPTION_ON_CONSUME_PURCHASE, false)) {
                    throw new RemoteException();
                }
                return bundle.getInt(CONSUME_PURCHASE, 0);
            }

            @Override
            public int stub(int apiVersion, String packageName, String type) throws RemoteException {
                return 0;
            }

            @Override
            public Bundle getBuyIntentToReplaceSkus(int apiVersion,
                                                    String packageName,
                                                    List<String> oldSkus,
                                                    String newSku,
                                                    String type,
                                                    String developerPayload) throws RemoteException {

                if (bundle.getBoolean(THROW_REMOTE_EXCEPTION_ON_GET_ACTIONS, false)) {
                    throw new RemoteException();
                }
                return bundle.getParcelable(GET_BUY_INTENT_TO_REPLACE_SKUS);
            }
        };
    }
}