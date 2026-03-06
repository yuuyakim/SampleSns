const API_BASE = 'http://localhost:8080/api';

export interface UserData {
  id: string;
  username: string;
  email: string;
  displayName: string;
  bio: string | null;
  createdAt: string;
}

export interface PostData {
  id: string;
  userId: string;
  content: string;
  createdAt: string;
  likeCount: number;
  liked: boolean;
}

export interface FollowData {
  userId: string;
  createdAt: string;
}

async function request<T>(url: string, options?: RequestInit): Promise<T> {
  const userId = localStorage.getItem('userId');
  const headers: Record<string, string> = {
    'Content-Type': 'application/json',
    ...(userId ? { 'X-User-Id': userId } : {}),
    ...(options?.headers as Record<string, string> || {}),
  };

  const res = await fetch(`${API_BASE}${url}`, { ...options, headers });

  if (!res.ok) {
    const body = await res.json().catch(() => ({}));
    throw new Error(body.error || `Request failed: ${res.status}`);
  }

  if (res.status === 204) return undefined as T;
  return res.json();
}

// User API
export const api = {
  register: (username: string, email: string, password: string) =>
    request<UserData>('/users', {
      method: 'POST',
      body: JSON.stringify({ username, email, password }),
    }),

  login: (email: string, password: string) =>
    request<UserData>('/auth/login', {
      method: 'POST',
      body: JSON.stringify({ email, password }),
    }),

  getUser: (userId: string) =>
    request<UserData>(`/users/${userId}`),

  updateProfile: (userId: string, displayName: string, bio: string) =>
    request<UserData>(`/users/${userId}`, {
      method: 'PUT',
      body: JSON.stringify({ displayName, bio }),
    }),

  // Post API
  createPost: (content: string) =>
    request<PostData>('/posts', {
      method: 'POST',
      body: JSON.stringify({ content }),
    }),

  deletePost: (postId: string) =>
    request<void>(`/posts/${postId}`, { method: 'DELETE' }),

  getUserPosts: (userId: string) =>
    request<PostData[]>(`/users/${userId}/posts`),

  getHomeTimeline: () =>
    request<PostData[]>('/timeline/home'),

  getGlobalTimeline: () =>
    request<PostData[]>('/timeline/global'),

  // Follow API
  follow: (userId: string) =>
    request<void>(`/users/${userId}/follow`, { method: 'POST' }),

  unfollow: (userId: string) =>
    request<void>(`/users/${userId}/follow`, { method: 'DELETE' }),

  getFollowers: (userId: string) =>
    request<FollowData[]>(`/users/${userId}/followers`),

  getFollowing: (userId: string) =>
    request<FollowData[]>(`/users/${userId}/following`),

  // Like API
  like: (postId: string) =>
    request<void>(`/posts/${postId}/like`, { method: 'POST' }),

  unlike: (postId: string) =>
    request<void>(`/posts/${postId}/like`, { method: 'DELETE' }),
};
