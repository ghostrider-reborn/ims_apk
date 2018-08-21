/*
 * Copyright (c) 2015, 2016 Qualcomm Technologies, Inc.
 * All Rights Reserved.
 * Confidential and Proprietary - Qualcomm Technologies, Inc.
 * Not a Contribution, Apache license notifications and license are retained
 * for attribution purposes only.
 *
 * Copyright (c) 2013 The Android Open Source Project
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

package org.codeaurora.ims;

import android.telephony.ims.ImsCallProfile;
import android.telephony.ims.ImsConferenceState;
import android.telephony.ims.ImsReasonInfo;
import android.telephony.ims.ImsStreamMediaProfile;
import android.telephony.ims.ImsSuppServiceNotification;
import com.android.ims.internal.IImsCallSession;
import com.android.ims.internal.IImsCallSessionListener;
import com.qualcomm.ims.utils.Log;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.RemoteException;

public class ImsCallSessionListenerProxy extends IImsCallSessionListener.Stub {
    public IImsCallSessionListener mListener;

    private HandlerThread mHandlerThread;
    private Handler mCallbackHandler;

    public ImsCallSessionListenerProxy() {
        Log.i(this, "Constructor: start handler thread for callbacks.");
        mHandlerThread = new HandlerThread(ImsCallSessionListenerProxy.class.getSimpleName());
        mHandlerThread.start();
        mCallbackHandler = new Handler(mHandlerThread.getLooper());
    }

    public ImsCallSessionListenerProxy(ImsCallSessionListenerProxy copyFrom) {
        Log.i(this, "Copy Constructor: Pass the thread and callback handler refs.");
        this.mListener = copyFrom.mListener;
        mHandlerThread = new HandlerThread(ImsCallSessionListenerProxy.class.getSimpleName());
        mHandlerThread.start();
        mCallbackHandler = new Handler(mHandlerThread.getLooper());
    }

    @Override
    public void callSessionStartFailed(final IImsCallSession session,
            final ImsReasonInfo reason) {
        if (mListener != null) {
            final Runnable r = new Runnable() {
                @Override
                public void run() {
                    try {
                        mListener.callSessionStartFailed(session, reason);
                    } catch (Throwable t) {
                        handleError(t, "onCallSessionStartFailed()");
                    }
                }
            };
            postRunnable(r);
        }
    }

    @Override
    public void callSessionProgressing(final IImsCallSession session,
            final ImsStreamMediaProfile profile) {

        if (mListener != null) {
            final Runnable r = new Runnable() {
                @Override
                public void run() {
                    try {
                        Log.i(this, "callSessionProgressing()");
                        mListener.callSessionProgressing(session, profile);
                    } catch (Throwable t) {
                        handleError(t, "onCallSessionProgressing()");
                    }
                }
            };
            postRunnable(r);
        }
    }

    @Override
    public void callSessionStarted(final IImsCallSession session,
            final ImsCallProfile profile) {

        if (mListener != null) {
            final Runnable r = new Runnable() {
                @Override
                public void run() {
                    try {
                        Log.i(this, "callSessionStarted()");
                        mListener.callSessionStarted(session, profile);
                    } catch (Throwable t) {
                        handleError(t, "onCallSessionStarted()");
                    }
                }
            };
            postRunnable(r);
        }
    }

    @Override
    public void callSessionTerminated(final IImsCallSession session,
            final ImsReasonInfo reason) {
        if (mListener != null) {
            final Runnable r = new Runnable() {
                @Override
                public void run() {
                    try {
                        Log.i(this, "callSessionTerminated()");
                        mListener.callSessionTerminated(session, reason);
                    } catch (Throwable t) {
                        handleError(t, "onCallSessionTerminated()");
                    }
                }
            };
            postRunnable(r);
        }
    }

    @Override
    public void callSessionUssdMessageReceived(IImsCallSession session,
            int mode, String ussdMessage) {
        //TODO - add dummy for compiling
    }

    @Override
    public void callSessionHandover(final IImsCallSession session,
            final int srcAccessTech, final int targetAccessTech, final ImsReasonInfo reasonInfo) {
        if (mListener != null) {
            final Runnable r = new Runnable() {
                @Override
                public void run() {
                    try {
                        Log.i(this, "callSessionHandover()");
                        mListener.callSessionHandover(session, srcAccessTech, targetAccessTech,
                            reasonInfo);
                    } catch (Throwable t) {
                        handleError(t, "callSessionHandover()");
                    }
                }
            };
            postRunnable(r);
        }
    }

    @Override
    public void callSessionHandoverFailed(final IImsCallSession session,
            final int srcAccessTech, final int targetAccessTech, final ImsReasonInfo reasonInfo) {
        if (mListener != null) {
            final Runnable r = new Runnable() {
                @Override
                public void run() {
                    try {
                        Log.i(this, "callSessionHandoverFailed()");
                        mListener.callSessionHandoverFailed(session, srcAccessTech,
                            targetAccessTech, reasonInfo);
                    } catch (Throwable t) {
                        handleError(t, "callSessionHandoverFailed()");
                    }
                }
            };
            postRunnable(r);
        }
    }

    @Override
    public void callSessionTtyModeReceived(final IImsCallSession session,
            final int ttyMode) {
        if (mListener != null) {
            final Runnable r = new Runnable() {
                @Override
                public void run() {
                    try {
                        Log.i(this, "callSessionTtyModeReceived");
                        mListener.callSessionTtyModeReceived(session, ttyMode);
                    } catch (Throwable t) {
                        handleError(t, "onCallSessionTtyModeReceived()");
                    }
                }
            };
            postRunnable(r);
        }
    }

    /**
     * Notifies the result of deflect request.
     */
    /*TODO: Remove this comment added for bringup @Override
    public void callSessionDeflected(final IImsCallSession session) {
        if (mListener != null) {
            final Runnable r = new Runnable() {
                @Override
                public void run() {
                    try {
                        mListener.callSessionDeflected(session);
                    } catch (Throwable t) {
                        handleError(t, "onCallSessionDeflected()");
                    }
                }
            };
            postRunnable(r);
        }
    }

    @Override
    public void callSessionDeflectFailed(final IImsCallSession session,
            final ImsReasonInfo reasonInfo) {
        if (mListener != null) {
            final Runnable r = new Runnable() {
                @Override
                public void run() {
                    try {
                        mListener.callSessionDeflectFailed(session, reasonInfo);
                    } catch (Throwable t) {
                        handleError(t, "onCallSessionDeflectFailed()");
                    }
                }
            };
            postRunnable(r);
        }
    }*/

    //TODO: Implement all call backs below
    /**
     * Notifies the result of the call hold/resume operation.
     */
    @Override
    public void callSessionHeld(final IImsCallSession session,
            final ImsCallProfile profile) {
        if (mListener != null) {
            final Runnable r = new Runnable() {
                @Override
                public void run() {
                    try {
                        Log.i(this, "callSessionHeld()");
                        mListener.callSessionHeld(session, profile);
                    } catch (Throwable t) {
                        handleError(t, "onCallSessionHeld()");
                    }
                }
            };
            postRunnable(r);
        }
    }

    @Override
    public void callSessionHoldFailed(final IImsCallSession session,
            final ImsReasonInfo reasonInfo) {
        if (mListener != null) {
            final Runnable r = new Runnable() {
                @Override
                public void run() {
                    try {
                        mListener.callSessionHoldFailed(session, reasonInfo);
                    } catch (Throwable t) {
                        handleError(t, "onCallSessionHoldFailed()");
                    }
                }
            };
            postRunnable(r);
        }
    }

    @Override
    public void callSessionHoldReceived(final IImsCallSession session,
            final ImsCallProfile profile) {
        if (mListener != null) {
            final Runnable r = new Runnable() {
                @Override
                public void run() {
                    try {
                        mListener.callSessionHoldReceived(session, profile);
                    } catch (Throwable t) {
                        handleError(t, "onCallSessionHoldReceived()");
                    }
                }
            };
            postRunnable(r);
        }
    }

    public void callSessionResumed(final IImsCallSession session,
            final ImsCallProfile profile) {
        if (mListener != null) {
            final Runnable r = new Runnable() {
                @Override
                public void run() {
                    try {
                        mListener.callSessionResumed(session, profile);
                    } catch (Throwable t) {
                        handleError(t, "onCallSessionResumed()");
                    }
                }
            };
            postRunnable(r);
        }
    }

    public void callSessionResumeFailed(final IImsCallSession session,
            final ImsReasonInfo reasonInfo) {
        if (mListener != null) {
            final Runnable r = new Runnable() {
                @Override
                public void run() {
                    try {
                        mListener.callSessionResumeFailed(session, reasonInfo);
                    } catch (Throwable t) {
                        handleError(t, "onCallSessionResumeFailed()");
                    }
                }
            };
            postRunnable(r);
        }
    }

    public void callSessionResumeReceived(final IImsCallSession session,
            final ImsCallProfile profile) {
        if (mListener != null) {
            final Runnable r = new Runnable() {
                @Override
                public void run() {
                    try {
                        mListener.callSessionResumeReceived(session, profile);
                    } catch (Throwable t) {
                        handleError(t, "onCallSessionResumeReceived()");
                    }
                }
            };
            postRunnable(r);
        }
    }

    /**
     * Notifies when the call session merge has started.
     *
     * @param session The session to merge.
     * @param newSession The new merged session.
     * @param profile The call profile.
     */
    public void callSessionMergeStarted(final IImsCallSession session,
            final IImsCallSession newSession, final ImsCallProfile profile) {
        if ( mListener != null ) {
            final Runnable r = new Runnable() {
                @Override
                public void run() {
                    try {
                        Log.i(this, "callSessionMergeStarted()");
                        mListener.callSessionMergeStarted(session, newSession, profile);
                    } catch (Throwable t) {
                        handleError(t, "onCallSessionMergeStarted()");
                    }
                }
            };
            postRunnable(r);
        }
    }

    /**
     * Notifies when the call session merge has successfully completed.
     *
     * @param session The merged call session.
     */
    public void callSessionMergeComplete(final IImsCallSession activeCallSession) {
        if (mListener != null) {
            final Runnable r = new Runnable() {
                @Override
                public void run() {
                    try {
                        Log.i(this, "callSessionMergeComplete()");
                        mListener.callSessionMergeComplete(activeCallSession);
                    } catch (Throwable t) {
                        handleError(t, "onCallSessionMergeComplete()");
                    }
                }
            };
            postRunnable(r);
        }
    }

    /**
     * Notifies when a call session merge has failed to complete.
     *
     * @param session The session which failed to merge.
     * @param reasonInfo The reason for the failure.
     */
    public void callSessionMergeFailed(final IImsCallSession session,
            final ImsReasonInfo reasonInfo) {
        if ( mListener != null ) {
            final Runnable r = new Runnable() {
                @Override
                public void run() {
                    try {
                        Log.i(this, "callSessionMergeFailed()");
                        mListener.callSessionMergeFailed(session, reasonInfo);
                    } catch (Throwable t) {
                        handleError(t, "oncallSessionMergeFailed()");
                    }
                }
            };
            postRunnable(r);
        }
    }

    /**
     * Notifies the result of call upgrade / downgrade or any other call updates.
     */
    public void callSessionUpdated(final IImsCallSession session,
            final ImsCallProfile profile){
        if (mListener != null) {
            final Runnable r = new Runnable() {
                @Override
                public void run() {
                    try {
                        mListener.callSessionUpdated(session, profile);
                    } catch (Throwable t) {
                        handleError(t, "onCallSessionResumeReceived()");
                    }
                }
            };
            postRunnable(r);
        }
    }

    /**
     * Notifies the supplementary service notification
     */
    public void callSessionSuppServiceReceived(final IImsCallSession session,
            final ImsSuppServiceNotification suppServiceInfo) {
        if (mListener != null) {
            final Runnable r = new Runnable() {
                @Override
                public void run() {
                    try {
                        mListener.callSessionSuppServiceReceived(session, suppServiceInfo);
                    } catch (Throwable t) {
                        handleError(t, "callSessionSuppServiceReceived()");
                    }
                }
            };
            postRunnable(r);
        }
    }

    public void callSessionUpdateFailed(IImsCallSession session,
            ImsReasonInfo reasonInfo){}
    public void callSessionUpdateReceived(IImsCallSession session,
            ImsCallProfile profile){}

    /**
     * Notifies the result of conference extension.
     */
    public void callSessionConferenceExtended(IImsCallSession session,
            IImsCallSession newSession, ImsCallProfile profile){}
    public void callSessionConferenceExtendFailed(IImsCallSession session,
            ImsReasonInfo reasonInfo){}
    public void callSessionConferenceExtendReceived(IImsCallSession session,
            IImsCallSession newSession, ImsCallProfile profile){}

    /**
     * Notifies the result of the participant invitation / removal to/from the conference session.
     */
    public void callSessionInviteParticipantsRequestDelivered(final IImsCallSession session){
        if (mListener != null) {
            final Runnable r = new Runnable() {
                @Override
                public void run() {
                    try {
                        Log.i(this, "callSessionInviteParticipantsRequestDelivered()");
                        mListener.callSessionInviteParticipantsRequestDelivered(session);
                    } catch (RemoteException re) {
                        Log.e(this, "RemoteException @" +
                                "callSessionInviteParticipantsRequestDelivered() --> " + re);
                    }
                }
            };
            postRunnable(r);
        }
    }

    public void callSessionInviteParticipantsRequestFailed(final IImsCallSession session,
            final ImsReasonInfo reasonInfo){
        if (mListener != null) {
            final Runnable r = new Runnable() {
                @Override
                public void run() {
                    try {
                        Log.i(this, "callSessionInviteParticipantsRequestFailed()");
                        mListener.callSessionInviteParticipantsRequestFailed(session, reasonInfo);
                    } catch (RemoteException re) {
                        Log.e(this, "RemoteException @" +
                                "callSessionInviteParticipantsRequestFailed() --> " + re);
                    }
                }
            };
            postRunnable(r);
        }
    }

    public void callSessionRemoveParticipantsRequestDelivered(IImsCallSession session){}
    public void callSessionRemoveParticipantsRequestFailed(IImsCallSession session,
            ImsReasonInfo reasonInfo){}

    /**
     * Notifies the changes of the conference info. the conference session.
     */
    public void callSessionConferenceStateUpdated(final IImsCallSession session,
            final ImsConferenceState state) {
        if (mListener != null) {
            final Runnable r = new Runnable() {
                @Override
                public void run() {
                    try {
                        Log.i(this, "callSessionConferenceStateUpdated()");
                        mListener.callSessionConferenceStateUpdated(session, state);
                    } catch (RemoteException re) {
                        Log.e(this, "RemoteException @onCallConferenceStateUpdated() --> " + re);
                    }
                }
            };
            postRunnable(r);
        }
    }

    /**
     * Notifies the retry error code
     */
    public void callSessionRetryErrorReceived(final IImsCallSession session,
            final ImsReasonInfo reasonInfo){
        if (mListener != null) {
            final Runnable r = new Runnable() {
                @Override
                public void run() {
                    try {
                        //TODO - Compilation stub mListener.callSessionRetryErrorReceived(session, reasonInfo);
                    } catch (Throwable t) {
                        handleError(t, "callSessionRetryErrorReceived()");
                    }
                }
            };
            postRunnable(r);
        }
    }

   /**
     * Notifies of a change to the multiparty state for this {@code ImsCallSession}.
     *
     * @param session The call session.
     * @param isMultiParty {@code true} if the session became multiparty, {@code false} otherwise.
     */
    public void callSessionMultipartyStateChanged(final IImsCallSession session,
            final boolean isMultiParty) {

        if (mListener != null) {
            final Runnable r = new Runnable() {
                @Override
                public void run() {
                    try {
                        Log.i(this, "callSessionMultipartyStateChanged()");
                        mListener.callSessionMultipartyStateChanged(session, isMultiParty);
                    } catch (Throwable t) {
                        handleError(t, "callSessionMultipartyStateChanged()");
                    }
                }
            };
            postRunnable(r);
        }
    }

    public void dispose() {
        Log.i(this, "dispose");
        final Runnable r = new Runnable() {
            @Override
            public void run() {
                cleanup();
            }
        };
        postRunnable(r);
    }

    private void cleanup() {
        Log.i(this, "cleanup");
        if (mHandlerThread != null) {
            mHandlerThread.quitSafely();
            mHandlerThread = null;
            mCallbackHandler = null;
        }
    }

    void postRunnable(Runnable r) {
        Log.i(this, "posting to handler");
        if (mCallbackHandler != null) {
            mCallbackHandler.post(r);
        }
    }

    private void handleError(Throwable t, String message) {
       Log.e(this, t + " " + message);
    }
}
