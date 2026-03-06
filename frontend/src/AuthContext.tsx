import { createContext, useContext, useState, useEffect, ReactNode } from 'react';
import { api, UserData } from './api';

interface AuthContextType {
    user: UserData | null;
    login: (email: string, password: string) => Promise<void>;
    register: (username: string, email: string, password: string) => Promise<void>;
    logout: () => void;
    loading: boolean;
}

const AuthContext = createContext<AuthContextType | null>(null);

export function AuthProvider({ children }: { children: ReactNode }) {
    const [user, setUser] = useState<UserData | null>(null);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const userId = localStorage.getItem('userId');
        if (userId) {
            api.getUser(userId)
                .then(setUser)
                .catch(() => {
                    localStorage.removeItem('userId');
                })
                .finally(() => setLoading(false));
        } else {
            setLoading(false);
        }
    }, []);

    const login = async (email: string, password: string) => {
        const userData = await api.login(email, password);
        localStorage.setItem('userId', userData.id);
        setUser(userData);
    };

    const register = async (username: string, email: string, password: string) => {
        const userData = await api.register(username, email, password);
        localStorage.setItem('userId', userData.id);
        setUser(userData);
    };

    const logout = () => {
        localStorage.removeItem('userId');
        setUser(null);
    };

    return (
        <AuthContext.Provider value={{ user, login, register, logout, loading }}>
            {children}
        </AuthContext.Provider>
    );
}

export function useAuth() {
    const ctx = useContext(AuthContext);
    if (!ctx) throw new Error('useAuth must be used within AuthProvider');
    return ctx;
}
