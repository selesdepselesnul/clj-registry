(ns clj-registry.core
  (:import (com.sun.jna.platform.win32 Advapi32Util)
           (com.sun.jna.platform.win32 WinReg))
  (:require [clojure.string :as string]))

;; TODO :
;; HKEY_DYN_DATA
;; HKEY_LOCAL_MACHINE
;; HKEY_PERFORMANCE_DATA
;; HKEY_PERFORMANCE_NLSTEXT
;; HKEY_PERFORMANCE_TEXT
(defn get-reg-root [x]
  (cond
    (= x :HKCR) WinReg/HKEY_CLASSES_ROOT
    (= x :HKLM) WinReg/HKEY_LOCAL_MACHINE
    (= x :HKCU) WinReg/HKEY_CURRENT_USER
    (= x :HKCC) WinReg/HKEY_CURRENT_CONFIG
    (= x :HKU)  WinReg/HKEY_USERS
    :else nil))

(defn normalize-slash [x]
  (string/replace x #"/" "\\\\"))

(defn get-reg-val [root key value]
  "Get a registry value"
  (Advapi32Util/registryGetValue
   (get-reg-root root)
   (normalize-slash key)
   value))

(defn create-key [root key]
  "Create a registry key"
  (Advapi32Util/registryCreateKey
   (get-reg-root root)
   (normalize-slash key)))

(defn delete-key [root key]
  "Delete a registry key"
  (Advapi32Util/registryDeleteKey
   (get-reg-root root)
   (normalize-slash key)))
