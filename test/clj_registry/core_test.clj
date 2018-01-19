(ns clj-registry.core-test
  (:import (com.sun.jna.platform.win32 WinReg))
  (:require [clojure.test :refer :all]
            [clj-registry.core :refer :all]))

(deftest get-reg-root-test
  (testing "get registry root"
    (is (= (get-reg-root :HKCR)
           WinReg/HKEY_CLASSES_ROOT))
    (is (= (get-reg-root :HKLM)
           WinReg/HKEY_LOCAL_MACHINE))
    (is (= (get-reg-root :HKCU)
           WinReg/HKEY_CURRENT_USER))
    (is (= (get-reg-root :HKCC)
           WinReg/HKEY_CURRENT_CONFIG))
    (is (= (get-reg-root :HKU)
           WinReg/HKEY_USERS))
    (is (nil? (get-reg-root :ANYTHING)))))

(deftest normalize-slash-test
  (testing "normalize slash"
    (is (= "SOFTWARE\\Microsoft\\Windows NT\\CurrentVersion"
           (normalize-slash "SOFTWARE/Microsoft/Windows NT/CurrentVersion")))))

(defn test-setup
  [f]
  (f)
  (delete-reg :HKCU "Software/Test"))

(deftest create-reg-test
  (testing "create registry"
    (is (create-reg :HKCU "Software/Test"))
    (is (not (create-reg :HKCU "Software/Test")))))

(use-fixtures :once test-setup)
